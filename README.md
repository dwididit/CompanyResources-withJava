
# Company Resources with Java Spring Boot

This Spring Boot project provides a REST API for managing company resources, specifically items and associated units of measurement (satuan). It utilizes MySQL for data persistence and Swagger UI for interactive API documentation.


## Technologies

**Spring Boot:** Simplifies development of web applications and REST APIs.

**MySQL** Relational database for storing company resource data.

**Swagger UI:** Dynamically generates user-friendly documentation for the API endpoints.


## Prerequisites

Java Development Kit (JDK) 17 or above

Maven

MySQL Server running locally or remotely





## Setup

Clone the project

```bash
  git clone git@github.com:dwididit/CompanyResources-withJava.git
```

## Configure Database
Create a database schema (e.g., company_resources) in your MySQL instance.

In the application.properties file (under src/main/resources), update the following properties:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/company_resources
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**Hibernate Configuration**
```bash
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

## Build and Run

Build the project with Maven:
```bash
  mvn clean package
```
Run the application:
```bash
  java -jar target/testhsm-0.0.1-SNAPSHOT.jar
```

## Accessing the API

**Base URL:** Typically http://localhost:8080 unless you've configured a different port.

**Swagger UI:** Access interactive API documentation at http://localhost:8080/swagger-ui/index.html

## Example API Endpoints
**Item Endpoints**

```bash
GET /item: Retrieve a list of all items (paginated)

GET /item/{id}: Fetch an item by its ID

POST /item: Create a new item

PUT /item/{id}: Update an existing item

DELETE /item/{id}: Delete an item
```

**Satuan Endpoints**
```bash
GET /satuan: Retrieve a list of all units of measurement (paginated)

GET /satuan/{id}: Fetch a satuan by its ID

POST /satuan: Create a new satuan

PUT /satuan/{id}: Update an existing satuan

DELETE /satuan/{id}: Delete a satuan
```

## Additional Notes

API endpoints in the controllers are annotated with @Tag from Swagger to organize documentation.
Error handling is implemented for common scenarios (e.g., resource not found)

## Contributing

Contributions and improvements are welcome! Please follow the standard fork-and-pull-request workflow.