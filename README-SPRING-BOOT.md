# Banking Management System - Spring Boot REST API

## 🚀 Features

- **RESTful API** for all banking operations
- **JPA/Hibernate** for database management
- **H2 in-memory database** (for development)
- **Spring Security** integration
- **Validation** for all inputs
- **Transaction management**
- **Account types**: Savings & Current
- **Full CRUD operations**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- (Optional) MySQL for production

## 🔧 Setup & Run

### 1. Build the project
```bash
cd "c:\Users\kruth\Downloads\Banking Management System"
mvn clean install
```

### 2. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### Health Check
```
GET /api/accounts/health
```

### Create Account
```
POST /api/accounts/create
Content-Type: application/json

{
  "accountNumber": 1001,
  "password": "pass123",
  "initialBalance": 1000.0,
  "accountType": "SAVINGS"
}
```

### Get Account
```
GET /api/accounts/{accountNumber}
```

### Get All Accounts
```
GET /api/accounts
```

### Authenticate
```
POST /api/accounts/authenticate
Content-Type: application/json

{
  "accountNumber": 1001,
  "password": "pass123"
}
```

### Deposit Money
```
POST /api/accounts/deposit
Content-Type: application/json

{
  "accountNumber": 1001,
  "amount": 500.0
}
```

### Withdraw Money
```
POST /api/accounts/withdraw
Content-Type: application/json

{
  "accountNumber": 1001,
  "amount": 200.0
}
```

### Transfer Money
```
POST /api/accounts/transfer
Content-Type: application/json

{
  "accountNumber": 1001,
  "targetAccountNumber": 1002,
  "amount": 100.0
}
```

### Apply Interest (Savings only)
```
POST /api/accounts/{accountNumber}/interest
```

### Get Transactions
```
GET /api/accounts/{accountNumber}/transactions
```

### Change Password
```
POST /api/accounts/{accountNumber}/change-password
Content-Type: application/json

{
  "oldPassword": "pass123",
  "newPassword": "newpass456"
}
```

## 🗄️ Database

### H2 Console (Development)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:bankingdb`
- Username: `sa`
- Password: (leave empty)

### MySQL (Production)
Uncomment MySQL configuration in `application.properties` and comment out H2 configuration.

## 📦 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/banking/
│   │       ├── BankingManagementSystemApplication.java
│   │       ├── config/
│   │       │   └── SecurityConfig.java
│   │       ├── controller/
│   │       │   └── AccountController.java
│   │       ├── dto/
│   │       │   ├── AccountDTO.java
│   │       │   ├── CreateAccountRequest.java
│   │       │   └── TransactionRequest.java
│   │       ├── entity/
│   │       │   ├── Account.java
│   │       │   └── Transaction.java
│   │       ├── repository/
│   │       │   ├── AccountRepository.java
│   │       │   └── TransactionRepository.java
│   │       └── service/
│   │           └── AccountService.java
│   └── resources/
│       └── application.properties
└── test/
```

## 🧪 Testing with cURL

### Create account:
```bash
curl -X POST http://localhost:8080/api/accounts/create \
  -H "Content-Type: application/json" \
  -d "{\"accountNumber\":1001,\"password\":\"pass123\",\"initialBalance\":1000,\"accountType\":\"SAVINGS\"}"
```

### Deposit:
```bash
curl -X POST http://localhost:8080/api/accounts/deposit \
  -H "Content-Type: application/json" \
  -d "{\"accountNumber\":1001,\"amount\":500}"
```

### Check balance:
```bash
curl http://localhost:8080/api/accounts/1001
```

## 💡 Notes

- The GUI application (BankingGUI.java) still works independently
- This Spring Boot version provides REST APIs for integration with web/mobile apps
- Both versions can coexist in the same project
- For production, switch to MySQL database in application.properties

## 🔒 Security

- Currently configured to permit all requests for development
- For production, implement JWT authentication
- Add role-based access control (RBAC)
- Enable HTTPS

## 📝 License

This project is for educational purposes.
