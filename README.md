UMLStudio — Backend (Spring Boot)

Overview
--------
This repository contains the backend for UMLStudio — a small Spring Boot application that demonstrates a simple layered architecture (Controller → Service → Repository) for managing "projects" and now includes basic JWT-based authentication (register/login).

Technology
----------
- Java 21
- Spring Boot (web, validation, data-jpa)
- H2 (runtime) and MySQL connector available (MySQL config commented)
- Maven wrapper (mvnw)

Project structure (important files/folders)
------------------------------------------
- src/main/java/com/UMLStudio/backend/
  - BackendApplication.java
    - Purpose: Spring Boot application entry point. Boots the application.

  - controller/
    - ProjectController.java — REST controller for project endpoints.
    - AuthController.java
      - Purpose: Exposes authentication endpoints under `/auth` (register, login, isUserLoggedIn).
      - Collaborators: `AuthService`, `JwtUtil`.

  - service/
    - ProjectService.java — business logic for projects.
    - AuthService.java
      - Purpose: Handles registration and login logic: checks username/email uniqueness, hashes passwords, validates credentials, returns JWT tokens.
      - Collaborators: `UserRepository`, `JwtUtil`, `PasswordUtils`.

  - repository/
    - ProjectRepository.java — JPA repository for `Project`.
    - UserRepository.java — JPA repository for `User` (findByUsername, findByEmail, existsByUsername, existsByEmail).

  - model/
    - Project.java — JPA entity for project table.
    - User.java
      - Purpose: JPA entity for application users.
      - Fields: id, name, username (unique), email (unique), passwordHash.

  - dto/
    - ProjectRequest / ProjectResponse — project input/output DTOs.
    - RegisterRequest — request DTO for registration (name, username, email, password).
    - LoginRequest — request DTO for login (username or email + password).

  - exception/
    - ResourceNotFoundException.java — 404 wrapper.
    - GlobalExceptionHandler.java — centralizes validation and not-found responses.

  - security/
    - JwtUtil.java
      - Purpose: Minimal, self-contained JWT helper (HMAC-SHA256) implemented without external JWT libs. Generates tokens with claims: sub (username), userId, iat, exp.
      - Notes: Uses `jwt.secret` and `jwt.expiration-ms` from `application.properties`.
    - PasswordUtils.java
      - Purpose: PBKDF2 password hashing/verification utility. Returns/reads `iterations:salt:hash` format.
    - JwtAuthenticationFilter.java
      - Purpose: Servlet filter that validates Bearer JWTs on incoming requests and sets a request attribute `authenticatedUser` when valid.
      - Notes: This is a lightweight approach that does not depend on Spring Security types; it registers via `SecurityConfig`.
    - SecurityConfig.java
      - Purpose: Registers the `JwtAuthenticationFilter` as a servlet filter for all routes.

- src/main/resources/application.properties — contains H2 config, JWT properties, and commented MySQL instructions.

API Endpoints (exposed by ProjectController)
--------------------------------------------
- POST /api/projects
  - Description: Create a new project.
  - Request body: JSON matching `ProjectRequest` (name (required), description (optional)).
  - Responses: 201 Created with `ProjectResponse` on success or 400 Bad Request with validation errors.

- GET /api/projects
  - Description: List all projects.
  - Responses: 200 OK with array of `ProjectResponse`.

- GET /api/projects/{id}
  - Description: Get a single project by id.
  - Responses: 200 OK with `ProjectResponse` or 404 Not Found when not present.

Auth API (summary)
------------------
- POST /auth/register
  - Request: { "name": "Krisha", "email": "krisha@example.com", "username":"krish123", "password": "mypassword123" }
  - Success: 201 Created
    { "message": "User registered successfully", "status":"SUCCESS", "userId": <id> }
  - Error: 400 Bad Request
    { "message": "Username already in use" | "Email already in use", "status":"FAILED" }

- POST /auth/login
  - Request: { "username": "Krisha123", "email": "krishna@example.com", "password": "mypassword123" }
    (provide either `username` OR `email` plus `password`)
  - Success: 200 OK
    { "message": "User logged in successfully", "status":"SUCCESS", "token": "<jwt>" }
  - Error: 401 Unauthorized
    { "message": "Invalid Credentials", "status":"FAILED" }

- GET /auth/isUserLoggedIn
  - Header: Authorization: Bearer <token>
  - Success (valid token): { "message":"Token valid", "status":"SUCCESS", "loggedIn": true, "userId": <id> }
  - Error (missing/invalid): 401 Unauthorized with { "message":"No token provided" | "Invalid token", "status":"FAILED", "loggedIn": false }

Notes about JWT and passwords
-----------------------------
- `JwtUtil` included is a minimal implementation for development and testing only. For production use, prefer a battle-tested library (e.g., JJWT or Nimbus) and store the secret securely (environment variable or secrets manager), ensure the secret is long enough (>= 256 bits for HS256), and rotate it regularly.
- `PasswordUtils` uses PBKDF2WithHmacSHA256 with salt and iterations; adjust iterations to match your threat model and hardware.

Switching to MySQL
------------------
- `application.properties` contains commented example MySQL properties. To switch:
  1) Uncomment and configure the MySQL datasource URL/credentials.
  2) Set `spring.jpa.database-platform` to `org.hibernate.dialect.MySQL8Dialect` (or appropriate dialect).
  3) Ensure MySQL is running and accessible.

Build & run
-----------
From the project root (this repository contains the Maven wrapper):

Windows (cmd.exe):

mvnw clean package
mvnw spring-boot:run

Or, with global Maven installed:

mvn clean package
mvn spring-boot:run

Testing the APIs
----------------
Examples (use Postman or curl):

POST register
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Krisha","username":"krish123","email":"krisha@example.com","password":"mypassword123"}'
```

POST login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"krish123","password":"mypassword123"}'
```

GET isUserLoggedIn
```bash
curl -X GET http://localhost:8080/auth/isUserLoggedIn \
  -H "Authorization: Bearer <token>"
```

Next steps & recommendations
----------------------------
- Add integration tests for the auth flow (happy path + edge cases).
- Consider switching to a standard JWT library and re-introducing Spring Security for robust role-based access control.
- Store JWT secret outside of source code (env var / secrets manager).

(README updated to include authentication section.)
