# Expense Tracker API

A secure, RESTful backend for tracking personal income and expenses — built with **Spring Boot**, **Spring Security (JWT)**, and **PostgreSQL**. Users can sign up, log transactions, filter and search through them, and get instant financial summaries by category and overall balance.

This was built as a backend-depth project to practice JWT auth, dynamic querying with JPA Specifications, DTO-based architecture with MapStruct, and clean exception handling — the same core stack used across my other projects.

---

## Features

- JWT-based authentication (signup/login) with BCrypt password hashing
- Full CRUD on transactions, scoped to the authenticated user
- Soft delete for both users and transactions (no hard deletes)
- Dynamic filtering by category, transaction type, month/year, and keyword search — built with JPA Specifications
- Pagination and sorting on the transaction list endpoint
- Financial summary endpoints: overall income/expense/balance and category-wise breakdown (via aggregate JPQL queries)
- DTO ↔ Entity mapping with MapStruct (no manual boilerplate mapping)
- Centralized exception handling via `@RestControllerAdvice`
- Interactive API docs with Swagger / OpenAPI
- Dockerized for easy local runs and cloud deployment

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4 |
| Security | Spring Security + JWT (jjwt) |
| Persistence | Spring Data JPA, PostgreSQL |
| Mapping | MapStruct + Lombok |
| Docs | springdoc-openapi (Swagger UI) |
| Containerization | Docker |

---

## Architecture

```
Controller → Service → Repository → PostgreSQL
     ↑           ↓
   DTOs      MapStruct Mappers
```

- **Controllers** — expose REST endpoints, delegate to services, never touch entities directly.
- **Services** — hold business logic; every transaction/user operation is scoped via `JwtAuthUtil.getCurrentUserId()`.
- **Specifications** — `TransactionSpecification` builds dynamic JPA queries (category, type, keyword, date range) that compose based on which filters are present.
- **Mappers** — `TransactionMapper` / `UserMapper` (MapStruct) convert between entities and DTOs.
- **GlobalExceptionHandler** — converts `ResourceNotFoundException` → 404 and `BadRequestException` → 400 with a consistent error body.

---

## API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/signup` | Register a new user |
| POST | `/api/auth/login` | Log in and receive a JWT |

### Users
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users/me` | Get current user's profile |
| PATCH | `/api/users` | Update current user's profile |
| DELETE | `/api/users` | Soft-delete current user's account |

### Transactions
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/transactions` | Create a new transaction |
| GET | `/api/transactions/{id}` | Get a transaction by ID |
| GET | `/api/transactions` | List transactions (filter, search, paginate, sort) |
| PATCH | `/api/transactions/{id}` | Update a transaction |
| DELETE | `/api/transactions/{id}` | Soft-delete a transaction |

**Query params on `GET /api/transactions`:** `keyword`, `category`, `type`, `month`, `year`, `page`, `size`, `sortBy`, `dir`

### Summary
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/summary` | Total income, total expense, and balance |
| GET | `/api/summary/categories` | Spend breakdown grouped by category |

All authenticated routes expect:
```
Authorization: Bearer <token>
```

---

## Getting Started

### Prerequisites
- Java 21
- PostgreSQL running locally or remotely
- Maven (or use the bundled `mvnw`)

### Environment Variables
The app reads these at startup — set them in your environment or an `.env` file your runner loads:

```
DB_URL=jdbc:postgresql://localhost:5432/expense_tracker
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=your_long_random_secret_key
JWT_EXPIRATION=86400000
```

### Run locally

```bash
git clone https://github.com/krisharma955/Expense-Tracker-API.git
cd Expense-Tracker-API
./mvnw clean install
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Run with Docker

```bash
docker build -t expense-tracker-api .
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/expense_tracker \
  -e DB_USERNAME=your_db_username \
  -e DB_PASSWORD=your_db_password \
  -e JWT_SECRET=your_long_random_secret_key \
  -e JWT_EXPIRATION=86400000 \
  expense-tracker-api
```

### API Docs
Once running, Swagger UI is available at:
```
https://expense-tracker-api-ny5f.onrender.com/swagger-ui/index.html#/
```
---

## License
This project is open for learning purposes. Feel free to fork and build on it.
