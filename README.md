# User Management API

A Spring Boot application that provides a RESTful API for managing users with secure authentication and email-based confirmation workflows.

---

## ✨ Features

- **CRUD Operations on User Entity**
  - Create, Read, Update, Delete operations for `User`.
  
- **Authentication with JWT**
  - Secure login using JSON Web Tokens (JWT).
  - Role-based access control (default role: `ROLE_USER`).

- **Email Confirmation for User Updates**
  - Sends a confirmation email to the user's registered email address before applying updates.
  - Ensures integrity and user consent for critical account changes.

---

## 🔐 Authentication

- JWT-based authentication using `Authorization: Bearer <token>` header.
- Token is issued on successful login and required for all secured endpoints.

---

## 🧩 Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- H2 / MySQL (configurable)
- Java Mail Sender
- JWT (jjwt)

---

## 📧 Email Configuration

To enable email confirmation functionality, configure the following properties in `application.properties` or `application.yml`:

```properties
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-email@example.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
