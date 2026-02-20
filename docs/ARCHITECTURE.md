# Polbohub Backend Architecture

## 1. Stack Selection & Justification

**Selected Stack:** Java (Spring Boot)

**Justification:**
- **Scalability & Performance:** Java 21+ with Virtual Threads offers high-throughput concurrency, essential for handling multiple simultaneous scraping jobs and concurrent user requests during competition registration peaks.
- **Enterprise Maturity:** Spring Boot provides a robust ecosystem for security (Spring Security), data access (Spring Data JPA), and integration.
- **Architecture Enforcement:** Java's strong typing and package structure make it easier to enforce Hexagonal Architecture boundaries compared to dynamic languages.
- **Security:** Seamless integration with Keycloak via Spring Security OAuth2 Resource Server.
- **Maintainability:** Long-term project health is better supported by Java's static analysis tools and refactoring capabilities.

## 2. High-Level Architecture

The system follows a **Hexagonal Architecture (Ports & Adapters)** to decouple core business logic from external concerns.

```mermaid
graph TD
    Client[Web/Mobile Client] -->|REST API| WebAdapter[Web Adapter (Controllers)]
    Scraper[Scraping Job] -->|Scheduled| ScraperAdapter[Scraping Adapter]
    
    subgraph "Application Core"
        WebAdapter --> UseCases[Use Cases / Services]
        ScraperAdapter --> UseCases
        UseCases --> Domain[Domain Models]
        UseCases --> Ports[Output Ports (Interfaces)]
    end
    
    Ports -->|Implements| Persistence[Persistence Adapter (JPA)]
    Ports -->|Implements| AIClient[AI Service Adapter]
    Ports -->|Implements| Notification[Notification Adapter]
    
    Persistence --> DB[(PostgreSQL)]
    AIClient --> AI[AI Engine / External API]
```

## 3. Domain Model & Database Schema

**Core Entities:**
- **User:** Base user entity. Contains profile, license, birth date.
- **Competition:** Event container. Dates, location, rules.
- **Run:** Specific event within a competition (e.g., 50m Freestyle).
- **Inscription:** A user's registration for a competition. Contains multiple `InscriptionEntry` items (one per Run).
- **PersonalTime:** Historical record of a user's time for a specific style/distance.
- **OfficialResult:** Scraped result from federation, linked to User via license.

**Schema (Simplified):**
- `users` (id, license_number, birth_date, user_id, ...)
- `competitions` (id, name, start_date, ...)
- `runs` (id, competition_id, style, distance, ...)
- `inscriptions` (id, user_id, competition_id, status, ...)
- `inscription_entries` (id, inscription_id, run_id, proposed_time)
- `personal_times` (id, user_id, style, distance, time, date)

## 4. API Endpoints (Key Domains)

**User Domain:**
- `GET /api/v1/users/me` - Get current user profile
- `PUT /api/v1/users/me` - Update profile
- `GET /api/v1/users/{id}/times` - Get personal times

**Competition Domain:**
- `GET /api/v1/competitions` - List upcoming competitions
- `POST /api/v1/competitions/{id}/inscribe` - Register for runs
- `GET /api/v1/competitions/{id}/recommendations` - Get AI run suggestions

**Admin Domain:**
- `POST /api/v1/admin/competitions` - Create competition
- `POST /api/v1/admin/scrape/trigger` - Manually trigger scraping

## 5. Keycloak Integration Flow

1.  **Login:** Frontend redirects user to Keycloak.
2.  **Token:** User logs in, Keycloak issues JWT (Access Token).
3.  **Request:** Frontend sends JWT in `Authorization: Bearer <token>` header.
4.  **Validation:** Spring Security validates signature and expiration.
5.  **Role Mapping:** `SecurityConfig` extracts roles from `realm_access.roles` claim.
6.  **Context:** `SecurityContextHolder` is populated with `Authentication` object containing roles (USER, STAFF, ADMIN).

## 6. Scraping Strategy (fegan.org)

**Tools:** Jsoup (Java HTML Parser).
**Approach:**
1.  **Discovery:** Scrape the "Calendar" or "Results" index page to find competition links.
2.  **Extraction:** For each competition page, parse HTML tables.
    -   Identify "Run" (Event) details (Style, Distance, Category).
    -   Iterate rows for User results.
3.  **Matching:** Use `License Number` from the scraped row to find the `User` entity in DB.
4.  **Idempotency:**
    -   Check if `OfficialResult` already exists for (User + Run).
    -   Update if improved/changed, otherwise skip.
5.  **Resilience:**
    -   Exponential backoff for rate limiting.
    -   Dead Letter Queue (conceptually) for failed parse attempts.

## 7. AI Inscription Assistant Logic

**Goal:** Recommend runs to maximize team points.

**Logic Flow:**
1.  **Input Gathering:**
    -   User's personal bests.
    -   Recent official results of *other* teams (competitors).
    -   Teammates' intended inscriptions (if available) or best times.
2.  **Scoring Heuristic:**
    -   Calculate `ExpectedRank` for the user in each available Run based on history.
    -   Map `ExpectedRank` to `ProjectedPoints` (Federation rules).
    -   **Conflict Avoidance:** If a teammate is virtually guaranteed 1st place (Gold), and this user is likely 2nd (Silver), the value is high. If 3 teammates are already top 3, adding a 4th might yield 0 points (if rules limit scorers per team).
3.  **Optimization:**
    -   Simple Greedy Algorithm: Pick top N runs with highest `ProjectedPoints`.
    -   Constraint Check: Ensure `maxRunsPerUser` is not exceeded.
    -   Schedule Check: Ensure runs are not simultaneous (if time schedule known).
4.  **Explanation:**
    -   "Recommended 100m Fly because you are ranked 3rd in the league and can score 12 points."

## 8. Scalability Roadmap

1.  **Phase 1 (Monolith):** Current architecture. Single DB instance. Horizontal scaling of API instances behind Load Balancer.
2.  **Phase 2 (Read Replicas):** Separate Read/Write DB connections. Scraping and Reporting hit Read Replicas.
3.  **Phase 3 (Async Processing):** Move scraping and heavy AI calculation to a Queue (RabbitMQ/Kafka) and Worker service.
4.  **Phase 4 (Sharding):** If club grows to Federation level, shard `PersonalTime` and `OfficialResult` data by Year or Region.
