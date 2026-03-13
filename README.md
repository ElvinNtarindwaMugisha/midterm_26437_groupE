# Lost and Found ID Card Management System

A robust Spring Boot REST API designed to streamline the recovery of lost identity cards through a hierarchical administrative structure.

---

## Key Features

### Intelligent Location Hierarchy
- **Recursive Location Tracking**: Supports administrative levels from **Province -> District -> Sector -> Cell -> Village**.
- **Simplified Data Input**: Create users by providing only a **Village Code**; the system automatically resolves the entire parent chain.
- **Deep Search**: Retrieve all users at any administrative level (e.g., all users in a specific Province) using recursive path traversal.

### Integrated Card Lifecycle
- **Unified User Entity**: Centralized user management for card owners and finders.
- **Automated Workflow**:
  - **LOST**: Claims are generated automatically upon user registration.
  - **FOUND**: Finder reports a card by its ID number, updating statuses across the system.
  - **RETURNED**: Administrative staff finalize the return, closing the claim.

### Clean API Design
- **Null Suppression**: JSON responses are optimized to exclude null fields, keeping API outputs concise.
- **Secure Seeding**: Built-in automated database seeding with cascading cleanup to prevent foreign key violations.

---

## Technology Stack
- **Backend**: Java 17, Spring Boot 3.x
- **Persistence**: Spring Data JPA, Hibernate
- **Database**: PostgreSQL
- **Utilities**: Lombok, Jackson (JSON handling)

---

## Data Model

### Location Tree
The system uses a **Self-Referencing Relationship** to build the Rwanda administrative structure:
- `Location` (Self-join via `parent_id`)
- `Elocation` (Enum: PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE)

### Core Entities
- **User**: The base person entity (Card Owner).
- **Finder**: Extends User with contact and discovery details.
- **IDCard**: Tracks status (`ACTIVE`, `LOST`, `FOUND`, `RETURNED`).
- **Claim**: Links all parties involved in a recovery record.
- **Administration**: Manages the final return and verification process.

---

## Getting Started

### 1. Database Configuration
Create a PostgreSQL database named `lostfound_db` and update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lostfound_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Initialization (Seeding)
Initialize the administrative hierarchy by sending a POST request:
**POST** `http://localhost:8080/api/locations/seed`

### 3. Usage Flow
1. **POST /api/users**: Register a user who lost their card (use village code `KGR-GAT`).
2. **POST /api/finders**: Register discovery by a finder (include `cardNumber`).
3. **POST /api/administration**: Finalize returning the card to the owner.

---

## Core API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/locations/seed` | Reset and seed location data |
| `GET` | `/api/users/province/code/{code}` | Find users by Province code |
| `GET` | `/api/users` | List people who lost cards (Finders excluded) |
| `GET` | `/api/finders` | List all discovery reports |
| `GET` | `/api/claims` | View discovery progress |
| `POST` | `/api/administration` | Record returned items |
