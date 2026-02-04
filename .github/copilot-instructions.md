# Copilot instructions for Enterprise Manager

Purpose: Help AI coding agents be productive in this Spring Boot monolith.

- **Big picture**: This is a Spring Boot (Java 21) monolithic web app. Key layers:
  - Web (MVC controllers and Thymeleaf templates): see [src/main/java/io/github/ngthduongg623/enterprise_manager/controller/EmployeeController.java](src/main/java/io/github/ngthduongg623/enterprise_manager/controller/EmployeeController.java#L1)
  - REST API: [src/main/java/io/github/ngthduongg623/enterprise_manager/controller/EmployeeRestController.java](src/main/java/io/github/ngthduongg623/enterprise_manager/controller/EmployeeRestController.java#L1)
  - Service: interface + impl pattern (`EmployeeService` / `EmployeeServiceImpl`)
    - example: [src/main/java/io/github/ngthduongg623/enterprise_manager/service/EmployeeServiceImpl.java](src/main/java/io/github/ngthduongg623/enterprise_manager/service/EmployeeServiceImpl.java#L1)
  - Data layer: Spring Data JPA repositories (e.g. [src/main/java/io/github/ngthduongg623/enterprise_manager/dao/EmployeeRepository.java](src/main/java/io/github/ngthduongg623/enterprise_manager/dao/EmployeeRepository.java#L1))
  - Templates: Thymeleaf files in `src/main/resources/templates` (e.g. `list-employee.html`).

- **Typical data flows / examples**:
  - REST read: `GET /api/employees` → `EmployeeRestController.findAll()` → `EmployeeServiceImpl.findAll()` → `EmployeeRepository.findAll()` → DB
  - MVC flow: `GET /employees/list` → `EmployeeController.listEmployees()` → populate `Model` → return `list-employee` template

- **Build / run / test (Windows notes)**:
  - Build & run (dev): use the wrapper in project root

```powershell
./mvnw spring-boot:run
# or on Windows PowerShell
.\mvnw.cmd spring-boot:run
```

  - Package jar: `./mvnw package` (or `.\mvnw.cmd package`). Run with `java -jar target/enterprise-manager-0.0.1-SNAPSHOT.jar`.
  - Tests: `./mvnw test` / `.\mvnw.cmd test`.

- **Runtime config / integration points**:
  - Default DB: Microsoft SQL Server (connection in `src/main/resources/application.properties`). Example: `spring.datasource.url=jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=employee_directory`.
  - Default credentials in properties: database user `sa` / `Abcd@1234`; Spring in-memory user `admin` / `123456`. Change for production.
  - Password generation helper: `src/main/java/io/github/ngthduongg623/enterprise_manager/security/PasswordGenerator.java` (prints bcrypt hash).

- **Project-specific conventions & gotchas**:
  - Service naming: interface `XService` and implementation `XServiceImpl`.
  - Controllers: both MVC controllers (return template names) and REST controllers (prefixed `/api`) coexist. Be careful when changing routes.
  - Repository: use Spring Data JPA (`JpaRepository<T, Integer>`).
  - AOP: `aspect/LoggingAspect.java` + `PointCutExpression.java` are present; note `PointCutExpression` references old package names (`com.mrurespect.*`) — verify pointcut expressions before modifying aspect behavior.
  - Template bindings: `EmployeeController` populates `Model` attributes used by Thymeleaf templates under `src/main/resources/templates`.

- **Where to change common things**:
  - DB connection and JPA config: `src/main/resources/application.properties`.
  - Security tweaks: `src/main/java/io/github/ngthduongg623/enterprise_manager/security` (and Spring Security config files if present).
  - Controller logic: `src/main/java/io/github/ngthduongg623/enterprise_manager/controller`.

- **When editing code, follow these concrete checks**:
  - If adding an endpoint, update both MVC and REST controllers only when intended; prefer adding new REST routes under `/api`.
  - Confirm service tests compile against Java 21 and that Lombok (optional) is not required for the changed files.
  - If you modify AOP pointcuts, search for outdated package names in `aspect/PointCutExpression.java`.

If anything here is unclear or you want me to expand examples (unit test patterns, common debugging commands, or add missing references), tell me which area to iterate on.
