# Frontend Architecture & Design

## 1. High-Level Architecture
The frontend is built as a Single Page Application (SPA) using **React**, **TypeScript**, and **Vite**.
It follows a **Feature-Based Architecture** where domain logic, state, and UI specific to a feature are co-located, while shared resources (generic UI components, utilities, hooks) are kept global.

### Key Architectural Patterns
- **API-First**: All data fetching uses a generated OpenAPI client to ensure type safety and contract adherence.
- **Service Layer**: API calls are abstracted in a service layer (generated), and consumed via React Hooks (TanStack Query recommended for caching/state).
- **Authentication**: JWT-based auth managed via a global `AuthProvider`.
- **Component Design**: "Smart" containers (Pages/Features) vs "Dumb" presentational components (UI Library).

## 2. Folder Structure
```
frontend/
├── .storybook/          # Storybook configuration
├── public/              # Static assets
├── src/
│   ├── api/             # Generated OpenAPI client and config
│   │   ├── generated/   # Auto-generated code (do not edit)
│   │   └── client.ts    # Axios instance with interceptors
│   ├── assets/          # Images, global styles
│   ├── components/      # Shared UI components (Atomic/Generic)
│   │   ├── ui/          # Buttons, Inputs, Cards
│   │   ├── layout/      # TopBar, Sidebar
│   │   └── guards/      # AuthGuard, RoleGuard
│   ├── context/         # Global Contexts (Auth, Theme)
│   ├── features/        # Feature modules
│   │   ├── auth/        # Login page, auth logic
│   │   ├── competitions/# List, Create, Details
│   │   ├── profile/     # User profile
│   │   └── system/      # Admin user management
│   ├── hooks/           # Shared custom hooks
│   ├── router/          # Route definitions
│   ├── utils/           # Helper functions
│   ├── App.tsx          # Root component
│   └── main.tsx         # Entry point
└── package.json
```

## 3. Routing Structure
We use `react-router-dom` v6+.

- `/` -> **PublicLayout** -> `LoginPage` (Redirects to /home if logged in)
- `/*` -> **PrivateLayout** (TopBar included) -> `ProtectedRoutes`
    - `/home` -> `HomePage`
    - `/competitions` -> `CompetitionsPage`
    - `/competitions/create` -> `CreateCompetitionPage` (Guard: STAFF/ADMIN)
    - `/competitions/:id` -> `CompetitionDetailsPage`
    - `/profile` -> `ProfilePage`
    - `/system` -> `SystemPage` (Guard: ADMIN)

## 4. Authentication Flow
1.  **Login**: User submits credentials to `/auth/login`.
2.  **Token Storage**:
    -   JWT Access Token stored in `localStorage` (or HTTP-only cookie if backend supports). *For this implementation, we assume localStorage/memory for simplicity per "Attach token" requirement.*
3.  **Request Interception**:
    -   Axios interceptor attaches `Authorization: Bearer <token>` to every request.
    -   Response interceptor handles `401 Unauthorized` by clearing auth and redirecting to login.
4.  **Persistence**: `AuthProvider` initializes by checking storage for token validity on app load.

## 5. Role-Based Rendering
-   **RoleGuard Component**: Wraps routes or sections.
    ```tsx
    <RoleGuard allowedRoles={['STAFF', 'ADMIN']}>
      <Button>Create Competition</Button>
    </RoleGuard>
    ```
-   **Logic**: Checks user's role from the decoded JWT or User Profile against `allowedRoles`. If mismatch, renders `fallback` (null or "Access Denied").

## 6. OpenAPI Integration Strategy
-   **Tool**: `openapi-typescript-codegen`
-   **Workflow**:
    1.  Backend exposes `http://localhost:8080/v3/api-docs`.
    2.  `npm run generate-api` script pulls spec and generates TS services/models in `src/api/generated`.
    3.  Frontend code imports from `src/api/generated`.
    4.  The generated client is configured to use our custom Axios instance (with auth interceptors).

## 7. Scalability Roadmap
1.  **State Management**: Move from Context to Redux Toolkit or Zustand if complex cross-feature state grows.
2.  **Performance**: Implement Code Splitting (React.lazy) per feature route.
3.  **Testing**: Add Unit Tests (Vitest) and E2E (Playwright).
4.  **CI/CD**: Automate OpenAPI generation and type checking on build.
5.  **Micro-frontends**: If domains split significantly, decouple `features` into separate packages.
