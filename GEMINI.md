# PolboHub - Project Context & Guidelines

PolboHub is a polyglot laboratory project designed to manage sports data (players, teams, and statistics). It integrates a robust Java backend with specialized Node.js microservices for data scraping and AI-driven interactions.

## 🚀 Project Overview

The project is divided into three main components:
1.  **Core Backend (Java):** A Spring Boot 4.0.2 application managing the domain logic, persistence, and REST API.
2.  **PolboScrapper (Node.js/Fastify):** A specialized service for scraping swimming data from the "fegan" website using Playwright and Drizzle ORM.
3.  **PolboAgent (Node.js/LangChain):** An AI agent using LangChain and MCP (Model Context Protocol) to provide intelligent interactions over the project's data.

### Main Technologies
- **Java Stack:** Java 25, Spring Boot 4.0.2, Spring Security (OAuth2/OIDC with Keycloak), Spring Data JPA, Flyway, PostgreSQL.
- **Node.js Stack:** Fastify, Playwright (Scrapper), LangChain, Drizzle ORM, TypeScript.
- **Infrastructure:** Docker Compose, Keycloak (Authentication & Authorization).

---

## 🏗 Architecture

### Java Backend Modules
The backend follows a layered/modular structure:
- `:api`: OpenAPI 3.1 definitions and generated DTOs.
- `:domain`: Core business entities (User, Player, Team, Sport).
- `:application`: Business services and use cases.
- `:persistence`: JPA repositories and Flyway migrations (`src/main/resources/db/migration`).
- `:rest`: REST controllers implementing the API.
- `:src`: Main application entry point (`PolboHubApplication.java`) and configuration.

### Microservices
- `polboscrapper/`: Fastify application for web scraping.
- `polboagent/`: LangChain-based AI agent.

---

## 🛠 Building and Running

### Prerequisites
- JDK 25
- Node.js (v20+)
- Docker & Docker Compose

### Infrastructure
Start the database and Keycloak:
```bash
docker-compose up -d
```
- **PostgreSQL:** `localhost:5432` (User/Pass: `polbohub/polbohub`)
- **Keycloak:** `localhost:8081` (Admin: `admin/admin`)

### Java Backend
Build and run the Spring Boot application:
```bash
./gradlew bootRun
```
The API is available at `http://localhost:8080`.

### PolboScrapper
```bash
cd polboscrapper
npm install
npm run dev
```

### PolboAgent
```bash
cd polboagent
npm install
npm start
```

---

## 📝 Development Conventions

- **ID Strategy:** Use `UUID` for all primary keys.
- **API First:** Define new endpoints in `api/openapi.yaml` before implementation.
- **Database Migrations:** Use Flyway for Java persistence and Drizzle for the scrapper.
- **Security:** Protected by OAuth2 Resource Server (Keycloak). Ensure the `KEYCLOAK_ISSUER_URI` is correctly configured.
- **Code Style:** Standard Spring Boot conventions for Java; Fastify/TypeScript conventions for Node.js modules.
- **Testing:**
    - Java: `./gradlew test` (uses JUnit 5).
    - Scrapper: `npm test` (uses Node's native test runner).

---

## 📂 Key Files & Locations
- `persistence/src/main/resources/db/migration/V1_0_0__initdb.sql`: Database schema.
- `api/openapi.yaml`: API contract.
- `polboscrapper/src/db/schema/schema.ts`: Drizzle schema for scrapper.
- `docker-compose.yml`: Infrastructure orchestration.
- `src/main/resources/application.yaml`: Main application configuration.
