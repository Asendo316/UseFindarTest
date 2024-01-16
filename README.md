# Spring Boot Project with MySQL Database Connection

This repository contains a sample Spring Boot project with MySQL database connection. Follow the steps below to set up and run the project.

## Prerequisites

Ensure you have the following installed on your system:

- Java Development Kit (JDK) - [Download](https://www.oracle.com/java/technologies/javase-downloads.html)
- Maven - [Download](https://maven.apache.org/download.cgi)
- MySQL Database - [Download](https://dev.mysql.com/downloads/)

## Project Setup

### Step 1: Create a Spring Boot Project

Use [Spring Initializr](https://start.spring.io/) to create a new Spring Boot project. Configure the project with "Spring Web" and "Spring Data JPA" dependencies.

### Step 2: Import the Project

Extract the downloaded zip file and import the project into your preferred Integrated Development Environment (IDE).

### Step 3: Configure MySQL Database Connection

Open `src/main/resources/application.properties` and configure the MySQL database connection by adding the following properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

Replace your_database_name, your_mysql_username, and your_mysql_password with your MySQL database information.

### Step 4: Create Entity Class and Repository
Create a JPA entity class and a repository interface for your entity. See src/main/java/com/example/demo/model/YourEntity.java and src/main/java/com/example/demo/repository/YourEntityRepository.java for examples.

### Step 5: Build and Run
Build your project and run the Spring Boot application:

```properties
# Maven
mvn clean install
mvn spring-boot:run
```

### Step 5.1: Implemented Features
1. Signup
2. Login (With JWT & RBAC)
3. CRUD for Books
4. Swagger Documentation
5. Thymeleaf Portal
6. Unit Tests

### Step 6: Testing
Swagger API Documentation: Your Spring Boot application is now running at http://localhost:8083/swagger-ui.html#/
Application Portal: http://localhost:8083/web/signIn
Api Base Url: http://localhost:8083

### 7 : Res
Login:

![image](https://github.com/Asendo316/UseFindarTest/assets/18377936/603a5802-69b5-4f3b-b01f-f88a94a8fd80)

Signup:
![image](https://github.com/Asendo316/UseFindarTest/assets/18377936/7c61a128-99b5-4190-b831-b9f657a7af03)

Dashboard:
![image](https://github.com/Asendo316/UseFindarTest/assets/18377936/2efa79e6-bb71-4cfa-bbe7-6402dfa59ef9)

Swagger:
![image](https://github.com/Asendo316/UseFindarTest/assets/18377936/5b996706-f24f-40f7-96ab-d59d54f24240)

