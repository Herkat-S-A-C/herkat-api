# 📦 Herkat ERP - Backend

Backend of the management system (Mini ERP) for Herkat.
The software is designed to manage bottle sales, blowing services efficiently, machine sales, customer portfolio, and inventory control.

The system features a modular architecture, data validation, a robust role-based security scheme, and a set of RESTful endpoints for seamless integration.

---

### 🛠️ Technologies Used

#### Backend Core
- **☕ Java 17** – Main programming language.
- **🌱 Spring Boot 3.5.0** – Framework for building the application.
- **📊 Spring Data JPA** – Data persistence layer.
- **✅ Bean Validation** – For validating input data.
- **🧩 DTO Pattern** – For safe and efficient data transfer between layers.
- **🏗 Lombok** – To reduce repetitive boilerplate code.

#### Database
- **🗄️ PostgreSQL 15.10** – Relational database management system.
- **🔄 Flyway** – Tool for database schema migration.

#### Security
- **🔐 JWT (JSON Web Tokens)** – For stateless authentication and authorization.
- **🔑 BCrypt** – Algorithm for password hashing and encryption.
- **🛡 CORS** – Configuration to allow cross-origin requests.

#### Development Tools
- **🔧 Maven** – Tool for dependency management and project build.

---

### 🚀 Quick Start

#### Prerequisites
- ☕ Java 17 or higher.
- 🗄️ PostgreSQL 15 or higher.
- 🔧 Maven 3.9 or higher.

#### Clone the Repository
```bash
git clone [https://github.com/Herkat-S-A-C/herkat-api](https://github.com/Herkat-S-A-C/herkat-api)
cd herkat-api
```

---

### 🤝 Contribution
**Contribution Rules**

- 🌿 Always develop in a separate branch (never work on ``main``).
- 📝 Describe your changes in the Pull Request (if the commit title is not enough).
- 🎯 Only work on the tasks assigned to you.
- 💬 Comment your code, especially in the services, to keep business logic clear.

**Workflow**

1. 🌿 Create a new branch from ``main``:
```bash
git checkout main
git pull origin main
git checkout -b feature/new-feature
```

2. 💾 Commit your changes:
```bash
git commit -m "adding new feature"
```

3. 📤 Push your branch to the remote repository:
```bash
git push origin feature/new-feature
```

4. 🔄 Create a Pull Request (PR) in GitHub and describe the changes made.

5. 👀 Wait for code review before merging.
