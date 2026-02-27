# Secure Feedback Application

A simple web application for collecting user feedback with focus on secure implementation practices.

## Technology Stack

- Java 21
- Spring Boot 3.2.3
- Spring Security
- PostgreSQL 16
- Docker & Docker Compose
- Thymeleaf

## Project Structure

```
src/main/java/com/secureapp/
├── config/       SecurityConfig
├── controller/   RegistrationController
├── dto/          RegistrationForm
├── entity/       User
├── repository/   UserRepository
└── service/      UserService
```

## Quick Start

### With Docker

```bash
docker-compose up -d
```

Access at: http://localhost:8080

### Local Development

```bash
./mvnw spring-boot:run
```

Database will initialize from `init-db/01-init.sql`

## Database Schema

- `users` - User accounts (username, email, password, role, timestamps)
- `feedback` - User feedback entries (name, email, message, timestamp)

## Configuration

Edit `.env` file to change database credentials and application port.
