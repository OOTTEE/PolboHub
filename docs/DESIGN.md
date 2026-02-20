### Stack Selection & Justification

**Chosen Stack:** Java (Spring Boot)

**Justification:**
1. **Strong Type Safety & Maintainability:** Java's static typing and mature ecosystem are ideal for complex domain models like sports competition management.
2. **Hexagonal Architecture Support:** Spring Boot excels at implementing Clean/Hexagonal architecture, allowing for clear separation between core domain logic and infrastructure (Keycloak, Scraping, DB).
3. **Security:** Spring Security provides first-class support for OAuth2 and JWT, making Keycloak integration seamless.
4. **Data Integrity:** Java Persistence API (JPA) with Hibernate ensures strong referential integrity in a relational database.
5. **Background Processing:** Spring's `@Scheduled` and TaskExecutor support make it easy to implement robust scraping jobs.
6. **Scalability:** Spring Boot applications are easily containerized (Docker-ready) and can be scaled horizontally.

### High-Level Architecture

The system follows **Hexagonal Architecture** (Ports and Adapters):

- **Domain Layer:** Contains the business logic, entities, and domain services (User, Competition, AI Logic).
- **Application Layer:** Orchestrates the flow of data between the domain and external interfaces (Use Cases).
- **Infrastructure Layer:**
    - **Driving Adapters:** REST Controllers (Spring Web).
    - **Driven Adapters:** 
        - Persistence (Spring Data JPA / PostgreSQL).
        - Identity (Keycloak).
        - Scraping (JSoup / HttpClient).
        - AI (OpenAI API or local deterministic models).

### Domain Model & Database Schema

#### Entities:
- **User/User:** ID, Name, BirthDate, LicenseNumber (Unique), Status (Active/Inactive), Roles (USER, STAFF, ADMIN).
- **Competition:** ID, Name, Location, FederationRef, StartDate, EndDate, RegistrationDeadline, PaymentRequired.
- **Run:** ID, CompetitionID, Style, Distance, Category, ScheduledTime.
- **Inscription:** ID, UserID, CompetitionID, Status (PENDING, APPROVED, REJECTED), PaymentStatus.
- **InscriptionRun:** InscriptionID, RunID, ProposedTime.
- **PersonalTime:** ID, UserID, Style, Distance, Time, Date, IsOfficial.
- **Membership:** ID, UserID, Plan, ValidFrom, ValidUntil.
- **Payment:** ID, UserID, Amount, Date, Method, Status.

### Scraping Strategy (fegan.org)
- **Technology:** JSoup for HTML parsing or Playwright if JS rendering is needed.
- **Job Schedule:** Periodic sync (nightly) and manual trigger for specific competitions.
- **Idempotency:** Use `federation_reference` and `license_number` to avoid duplicate results.

### AI Inscription Assistant
- **Logic:**
    1. Filter eligible runs for a user.
    2. Rank runs based on personal bests vs. team records.
    3. Use a scoring algorithm to maximize team points while minimizing overlap with faster teammates.
- **Transparency:** Each recommendation includes a `reasoning` field explaining the point potential.
