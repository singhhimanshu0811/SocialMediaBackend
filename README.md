# Spring Boot Backend - Online Shopping Application

## Table of Contents
1. [Introduction](#introduction)  
2. [Features](#features)  
3. [Technologies Used](#technologies-used)  
4. [Database Design](#database-design)  
5. [Setup Instructions](#setup-instructions)  
6. [API Endpoints](#api-endpoints)  
7. [Contributing](#contributing)  
8. [License](#license)  

---

## Introduction
This is the backend service for an online shopping application, built using Spring Boot. It provides RESTful APIs for managing users, products, carts, orders, payments, and more, following a robust and scalable architecture.  

---

## Features
- **User Management**: Registration, login, and role-based access for buyers and sellers.  
- **Product Management**: Add, update, and browse products by category.  
- **Cart Management**: Add and update items in a shopping cart with real-time price calculation.  
- **Order Management**: Place orders, track status, and manage order items.  
- **Payment Processing**: Support for multiple payment methods.  
- **Address Management**: Associate multiple addresses with users.  
- **Category Management**: Manage product categories.  

---

## Technologies Used
- **Backend**: Spring Boot, Spring Data JPA, Spring Security  
- **Database**: MySQL or PostgreSQL  
- **Build Tool**: Maven  
- **Authentication**: JWT (JSON Web Tokens)  
- **APIs**: RESTful web services  

---

## Database Design
The database design includes the following tables:  
- `users` (manages user accounts)  
- `roles` (manages user roles such as buyer or seller)  
- `products` (manages product details)  
- `categories` (manages product categories)  
- `carts` (tracks items in the shopping cart)  
- `orders` (stores order details)  
- `payments` (stores payment information)  
- `addresses` (manages user addresses)  

### Key Relationships:
- **Many-to-many**: Between `users` and `roles`.  
- **One-to-many**: Between `users` and `addresses`.  
- **Many-to-many**: Between `products` and `categories`.  
- **One-to-many**: Between `carts` and `cart_items`.  
- **One-to-many**: Between `orders` and `order_items`.

---

## Setup Instructions
1. **Clone the Repository**:  
   ```bash
   git clone https://github.com/your-repo/shopping-backend.git
   cd shopping-backend
2. **Configure the Database**
Update `application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopping_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
```

**Build and Run**

```
mvn clean install
mvn spring-boot:run
```
**Access the API**
```
The application will be available at http://localhost:8080.
```
## API Endpoints
Here is a summary of the key endpoints:

### User Management
- `POST /api/auth/register` - Register a new user  
- `POST /api/auth/login` - Authenticate user  

### Product Management
- `GET /api/products` - Fetch all products  
- `POST /api/products` - Add a new product  

### Cart Management
- `POST /api/cart` - Add item to cart  
- `GET /api/cart` - View cart  

### Order Management
- `POST /api/orders` - Place an order  
- `GET /api/orders/{orderId}` - Get order details  

### Payment Management
- `POST /api/payments` - Process payment




