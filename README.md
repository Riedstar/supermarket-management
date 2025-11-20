# Supermarket Management System

## UI Evolution
- Basic (plain tables): [basic-version branch](tree/basic-version)
- Modern (animated cards): [main branch](tree/main)
[Screenshot: Before | After]

Spring Boot app for inventory CRUD, sales cart, reports, and role-based access.

## Demo
- [Live App](heroku-url-if-deployed) or [Local Video](link-to-loom).
- Test: admin/password (admin); cashier/password (view-only).

## Features
- Secure login (Spring Security).
- Product CRUD (JPA/Thymeleaf).
- Shopping cart & checkout (session-based).
- Low-stock reports.
- Roles: Admin (full), Cashier (view/cart).

## UI Evolution
- Basic (plain tables): [Branch](github.com/Riedstar/supermarket-management/tree/basic-version).
- Modern (animated cards, dark theme): [Main](github.com/Riedstar/supermarket-management/tree/main).
[Before/After Screenshot]

## Setup
1. Clone: `git clone https://github.com/Riedstar/supermarket-management.git`
2. MySQL: Create `supermarket_db`, run schema.
3. `./mvnw spring-boot:run`
