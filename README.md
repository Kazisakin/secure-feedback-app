# Secure Feedback App

A secure web-based application for CS4417 - Software Security (Winter 2026).

## Tech Stack

- Backend: Spring Boot (Java)
- Security: Spring Security
- Database: MariaDB
- Password Hashing: BCrypt
- Frontend: HTML, CSS, JavaScript
- Build Tool: Maven

## Features

- User registration and login
- Change password
- Role-based access control (User/Admin)
- Secure feedback submission
- Input validation

## Database Schema

### Users Table

| Column | Type | Description |
|--------|------|-------------|
| id | INT | Primary Key, Auto Increment |
| username | VARCHAR(50) | Unique, Not Null |
| email | VARCHAR(100) | Unique, Not Null |
| password_hash | VARCHAR(255) | Not Null |
| salt | VARCHAR(255) | Not Null |
| role | VARCHAR(20) | USER or ADMIN |
| created_at | TIMESTAMP | Auto-generated |

### Feedback Table

| Column | Type | Description |
|--------|------|-------------|
| id | INT | Primary Key, Auto Increment |
| user_id | INT | Foreign Key to Users |
| name | VARCHAR(100) | Not Null |
| email | VARCHAR(100) | Not Null |
| subject | VARCHAR(200) | Optional |
| message | TEXT | Not Null |
| submitted_at | TIMESTAMP | Auto-generated |

## Security Measures

- BCrypt password hashing
- Parameterized queries to prevent SQL injection
- Input validation and output encoding
- Generic error messages
- Least privilege principle
