# 🏦 Banking Management System

A modern, full-featured Banking Management System built with **Spring Boot 3.4.3** and **Java 21**. This application provides comprehensive banking operations including account management, transactions, and secure authentication.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## ✨ Features

### 🔐 Security
- Secure authentication with Spring Security
- Password-protected accounts
- Account locking after multiple failed attempts
- Session management

### 💰 Banking Operations
- **Create Account** - Open new bank accounts with initial deposit
- **Deposit Money** - Add funds to accounts
- **Withdraw Money** - Withdraw funds with balance validation
- **Transfer Funds** - Transfer money between accounts
- **Check Balance** - View current account balance
- **Transaction History** - View all account transactions
- **Unlock Account** - Unlock accounts after failed login attempts

### 📊 Account Management
- Multiple account types (Savings, Current)
- Unique account numbers
- Real-time balance tracking
- Transaction timestamps
- Account status monitoring

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/kruthikroshan/banking-management-system.git
cd banking-management-system
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

4. **Access the application**
- Web UI: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- API Endpoints: http://localhost:8080/api/accounts

### Default Credentials
- **Username**: `admin`
- **Password**: `admin123`

## 📡 API Endpoints

### Account Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/accounts` | Create new account |
| GET | `/api/accounts/{id}` | Get account by ID |
| GET | `/api/accounts` | List all accounts |
| POST | `/api/accounts/deposit` | Deposit money |
| POST | `/api/accounts/withdraw` | Withdraw money |
| POST | `/api/accounts/transfer` | Transfer funds |
| POST | `/api/accounts/unlock/{id}` | Unlock account |
| GET | `/api/accounts/{id}/transactions` | Get transaction history |
| POST | `/api/accounts/validate` | Validate account credentials |
| POST | `/api/accounts/{id}/change-password` | Change password |
| GET | `/api/accounts/summary` | Get account summary |

### Example Requests

**Create Account**
```json
POST /api/accounts
{
  "accountNumber": 123456,
  "accountType": "SAVINGS",
  "initialBalance": 1000.0,
  "password": "mypassword"
}
```

**Deposit Money**
```json
POST /api/accounts/deposit
{
  "accountId": 1,
  "amount": 500.0
}
```

**Transfer Funds**
```json
POST /api/accounts/transfer
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 200.0,
  "description": "Payment"
}
```

## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 3.4.3
- **Language**: Java 21
- **Database**: H2 (In-memory for development)
- **ORM**: Hibernate/JPA
- **Security**: Spring Security
- **Build Tool**: Maven
- **Template Engine**: Thymeleaf
- **Validation**: Jakarta Validation

## 📁 Project Structure

```
banking-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/banking/
│   │   │   ├── config/          # Security configuration
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── repository/      # Data repositories
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/       # Thymeleaf templates
├── pom.xml                      # Maven dependencies
├── railway.json                 # Railway deployment config
├── nixpacks.toml               # Build configuration
└── README.md
```

## 🌐 Deployment

### Deploy to Railway

This application is configured for easy deployment on [Railway](https://railway.app).

1. Fork this repository
2. Sign up on [Railway](https://railway.app) with GitHub
3. Create new project → Deploy from GitHub
4. Select your repository
5. Click Deploy

Your app will be live at: `https://your-app.up.railway.app`

**Environment Variables** (Optional):
- `ADMIN_USERNAME` - Custom admin username
- `ADMIN_PASSWORD` - Custom admin password
- `LOG_LEVEL` - Logging level (INFO/DEBUG)

See [RAILWAY_DEPLOYMENT.md](RAILWAY_DEPLOYMENT.md) for detailed instructions.

### Local Development

1. Update `application.properties` for your environment
2. Run with Spring Boot DevTools for hot reload
3. Access H2 Console at http://localhost:8080/h2-console

**H2 Database Connection:**
- JDBC URL: `jdbc:h2:mem:bankingdb`
- Username: `sa`
- Password: (leave empty)

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 📝 Configuration

Key configurations in `application.properties`:

```properties
# Server Port
server.port=8080

# Database (H2 In-Memory)
spring.datasource.url=jdbc:h2:mem:bankingdb

# JPA Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Security
spring.security.user.name=admin
spring.security.user.password=admin123
```

## 🔒 Security Features

- Basic Authentication with Spring Security
- Password encryption (can be enhanced with BCrypt)
- Account locking mechanism
- Wrong attempt tracking
- Session management
- CORS configuration for API access

## 🚧 Future Enhancements

- [ ] JWT authentication
- [ ] Email notifications
- [ ] SMS alerts
- [ ] Account statements (PDF)
- [ ] Loan management
- [ ] Credit/Debit card management
- [ ] Bill payments
- [ ] Investment tracking
- [ ] Multi-currency support
- [ ] Admin dashboard
- [ ] PostgreSQL integration

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Kruthik Roshan**
- GitHub: [@kruthikroshan](https://github.com/kruthikroshan)

## 🙏 Acknowledgments

- Spring Boot Team for the amazing framework
- H2 Database for quick prototyping
- Railway for easy deployment

## 📞 Support

If you have any questions or need help, please open an issue in the repository.

---

⭐ **Star this repository if you find it helpful!**
