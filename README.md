# Bookstore Cafe - Web Application

This project is a web application for a bookstore combined with a café, developed as part of an **engineering thesis**.  
The application enables product browsing, online ordering, seat reservations, and administrative management of orders and products.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot)
![React](https://img.shields.io/badge/React-61DAFB?style=flat&logo=react&logoColor=black)
![Stripe](https://img.shields.io/badge/Stripe-635BFF?style=flat&logo=stripe&logoColor=white)


---

## Project Objective

The objective of this project was to design and implement a web application for a bookstore-café using modern web technologies.  
The system consists of a front-end and back-end layer supported by a relational SQL database.

The project includes:
- requirements analysis,
- user interface design,
- database structure design,
- application implementation,
- integration with external services.

---

## Application Modules

### Customer Module
- browsing available products,
- adding products to the shopping cart,
- placing orders,
- seat reservation,
- online payments using Stripe,
- product rating system.

### Employee / Admin Module
- order management,
- product management,
- adding and editing products including images,
- integration with Google Cloud Storage.

---

## Technologies Used

### Front-end
- React
- React Router
- Axios
- FontAwesome
- Stripe
- React DatePicker

### Back-end
- Java 19
- Spring Boot
- REST API

### Database
- MySQL

### External Services
- Google Cloud Storage
- Stripe Payments

---

## System Requirements

Before running the application, ensure the following software is installed:

- Java 19.0.2  
- Node.js 16.16.0  
- MySQL Workbench 8.0 CE  
- Eclipse IDE for Java Development (2022-12)  
- Visual Studio Code  

> Note  
> When using newer versions of Java or Eclipse, warning messages related to deprecated Spring Boot methods may appear.  
> These warnings do not affect the functionality of the application.

---

## Running the Application

### Back-end Setup (Spring Boot)

1. Open Eclipse IDE
2. Install the plugin:
   - Spring Tools 4 (Spring Tool Suite 4) version 4.21.0.RELEASE
3. Restart Eclipse
4. Import the project:
   - Import → Existing Projects into Workspace
   - Select the directory:  
     `bookstore-cafe/bookstore-cafe`
5. Configure database connection in:
   ```properties
   application.properties
   spring.datasource.url=...
   spring.datasource.username=...
   spring.datasource.password=...
