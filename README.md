# UNIHUB - Student Academic Support System

A Spring Boot web application for managing students, teachers, attendance, assignments, and scholarships.

## Features

- Student Registration & Login
- Teacher Registration & Login
- Student Dashboard
- Teacher Dashboard
- Attendance Management
- Assignment Management
- Scholarship Management
- MySQL Database Integration

## Tech Stack

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- MySQL
- Maven
- HTML for Templates

## Project Structure

```plaintext
src/main/java/com/example/sampleapp
├── controller
├── model
├── repository
```

## Setup Instructions

### 1. Clone Repository

```bash
git clone <your-repo-url>
```

### 2. Configure Database

Create:

```plaintext
src/main/resources/application.properties
```

Add your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/login_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Import Database

Import:

```
login_db.sql
```

into MySQL.

### 4. Run Application

```bash
mvn spring-boot:run
```

## Templates

- login.html
- student-dashboard.html
- teacher-dashboard.html
- student-login.html
- teacher-login.html

## Author

Meril
