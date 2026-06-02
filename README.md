# 🏦 Bank Application – Spring Boot + Spring Security + JWT

A secure banking backend application built using Spring Boot, Spring Security, JWT Authentication, Hibernate (JPA), and MySQL.

This project demonstrates secure REST API development with role-based access, token-based authentication, and core banking operations.

---

## 🚀 Features

### 👤 User Module
- User Registration
- Login with JWT Token Generation
- Password Encryption using BCrypt
- Role-based Authorization
- Protected Endpoints

### 💳 Account Module
- Create Account for User
- View All Accounts
- Deposit Money
- Withdraw Money
- Check Account Balance

### 💸 Transaction Module
- Transfer Money Between Accounts
- View Transaction History
- View Transactions Between Two Accounts

### 🔐 Security Features
- JWT-based Authentication
- Stateless Session Management
- BCrypt Password Encoding
- Custom JWT Filter
- Token Validation on Each Request

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Hibernate / JPA
- MySQL
- Maven

---

## 📁 Project Structure (High-Level)

controller/
UserController
AccountController
TransactionController

service/
UserService
AccountService
TransactionService

repository/
UserRepository
AccountRepository
TransactionRepository

security/
JwtFilter
JwtUtil
SecurityConfig

dto/
LoginDetailsDTO
UserResponseDTO
AccountResponseDTO


---

## ⚙️ Database Setup

### 1️⃣ Create MySQL Database

```sql
CREATE DATABASE bankdb;
USE bankdb;
```
2️⃣ Configure application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
🔑 Authentication Flow (JWT)

User registers.

User logs in via /login.

JWT token is generated.

Client must send token in header for secured APIs:
```
Authorization: Bearer <your_token>
```
Spring Security validates token before allowing access.

📌 API Endpoints
🔹 User APIs
| Method | Endpoint | Description             |
| ------ | -------- | ----------------------- |
| POST   | /User    | Register new user       |
| POST   | /login   | Login & generate JWT    |
| GET    | /Users   | Get all users (secured) |

🔹 Account APIs
| Method | Endpoint                       | Description       |
| ------ | ------------------------------ | ----------------- |
| POST   | /Account/user/{id}             | Create account    |
| GET    | /Accounts                      | View all accounts |
| POST   | /deposit/{amount}/{accountNo}  | Deposit money     |
| POST   | /withdraw/{amount}/{accountNo} | Withdraw money    |
| GET    | /getBalance/{accountNo}        | Check balance     |

🔹 Transaction APIs
| Method | Endpoint                                              | Description                   |
| ------ | ----------------------------------------------------- | ----------------------------- |
| POST   | /doTransaction/{fromAccount}/{toAccount}/{amount}     | Transfer money                |
| GET    | /TransactionHistory/{accountNo}                       | Get account transactions      |
| GET    | /TransactionHistory/between/{fromAccount}/{toAccount} | Transactions between accounts |

🧪 Sample Login Request
Request
```
POST /login

{
  "username": "testuser",
  "password": "password123"
}
```
Response
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

🏗️ Business Flow

1.Register User

2.Login → Get JWT

3.Create Account

4.Deposit / Withdraw

5.Transfer Between Accounts

6.View Transaction History

📈 What This Project Demonstrates

1.REST API Design

2.Layered Architecture (Controller → Service → Repository)

3.Stateless Authentication using JWT

4.Secure Password Handling

5.Clean DTO Usage

6.Proper Separation of Concerns

🔮 Future Improvements

1.Refresh Token Implementation

2.Swagger API Documentation

3.Global Exception Handling (@ControllerAdvice)

4.Unit & Integration Testing

5.Role-based Endpoint Restriction (ADMIN / USER)

6.Docker Deployment

👨‍💻 Author

Tejesh Kadam
Backend Developer – Spring Boot | Security | REST APIs

GitHub: https://github.com/kadam-tejesh
