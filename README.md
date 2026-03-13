# Lost and Found ID Card Management System

This is a robust Spring Boot REST API designed to streamline the recovery of lost identity cards through a hierarchical administrative structure.

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

## Technical Implementation & Logic

This project is designed to meet specific academic requirements for database relationships and advanced Spring Data JPA features.

### Database Relationships
- **One-to-One**: Implemented between `User` and `IDCard`. One physical ID card is uniquely owned by exactly one student.
- **One-to-Many**: Implemented between `IDCard` and `Claim`. One ID card can have multiple discovery or recovery claims over its lifetime.
- **Many-to-Many**: Implemented between `Administration` and `Claim`. Staff handle multiple claims, and a claim can be processed by different staff members via a join table.

### Advanced Features
- **Hierarchical Locations**: Stored using a self-referencing hierarchy (`parent_id`) allowing for recursive traversal from Province down to Village.
- **Recursive Search**: The system uses a recursive "isChildOf" logic to find all users within a Province, even if they are linked only to a specific Village.
- **Optimized Existence Checks**: Uses `idCardRepository.existsByCardNumber()` for high-performance duplicate checking.
- **Sorting & Pagination**: The `/api/claims` endpoint implements `Pageable` and `Sort` to handle large datasets efficiently.

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
Initialize the administrative hierarchy and clear existing data:
**POST** `http://localhost:8080/api/locations/seed`

---

## Core API Endpoints

### Location & Search
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/locations/seed` | Reset and seed location hierarchy |
| `GET` | `/api/users/province/code/KGL` | Find all users in Kigali Province (by Code) |
| `GET` | `/api/users/province/name/Kigali City` | Find all users in Kigali Province (by Name) |
| `GET` | `/api/locations/province/KGL/users` | Alternative retrieval by location code |

### User & Finder
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/users` | List people who lost cards (Finders excluded) |
| `POST` | `/api/users` | Register a new user and create "LOST" claim |
| `GET` | `/api/finders` | List all discovery reports |
| `POST` | `/api/finders` | Register a found card (updates status to "FOUND") |

### Claims & Administration
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/claims?page=0&size=10&sortBy=claimId` | Paginated and Sorted claims |
| `POST` | `/api/administration` | Record returned items (updates status to "RETURNED") |
