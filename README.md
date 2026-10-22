
# Inventory Management System

A desktop-based Inventory Management System developed using Java Swing, JDBC, and MySQL to manage product records and monitor stock levels.

## 1. Project Details

- **Project Title:** Inventory Management System
- **Developed By:** Mahipal Singh
- **Technology:** Java Swing, JDBC, MySQL
- **IDE:** Visual Studio Code
- **Java Version:** JDK 8

## 2. Problem Statement

Managing product records manually can be time-consuming and may lead to errors in stock tracking. This project provides a simple desktop application to maintain product information and monitor available inventory.

## 3. Objectives

- Maintain product ID, product name, quantity, and price.
- Perform CRUD operations on product records.
- Search for products quickly.
- Identify products with low stock.
- Store product information in a MySQL database.

## 4. Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application programming |
| Java Swing | Graphical user interface |
| JDBC | Java-to-database connectivity |
| MySQL | Database management |
| MySQL Connector/J | JDBC driver for MySQL |
| VS Code | Development environment |

## 5. Features

- Add new products
- Search products
- Update product details
- Delete products
- View all products
- Low-stock alert for products with quantity of 5 or less
- Database-backed product management
- User-friendly graphical interface

## 6. Database Design

**Database:** `inventory_db`

**Table:** `products`

| Column | Data Type | Description |
|---|---|---|
| product_id | INT | Primary key |
| product_name | VARCHAR(100) | Product name |
| quantity | INT | Available stock |
| price | DECIMAL(10,2) | Product price |

The database stores product details and allows the application to retrieve and modify records using JDBC.

## 7. Project Structure

    InventoryManagementSystem/
    ├── Main.java
    ├── inventory_db.sql
    ├── README.md
    ├── .gitignore
    ├── lib/
    │   └── mysql-connector-j-26.7.0.jar
    └── screenshots/
        ├── main-screen.png
        ├── add-product.png
        ├── search-update.png
        └── low-stock.png

## 8. Application Screenshots

### Main Application
Displays the main GUI, product table, and available operations.

![Main Application](screenshots/main-screen.png)

### Add Product
Demonstrates adding a new product to the inventory.

![Add Product](screenshots/add-product.png)

### Search and Update
Demonstrates finding a product and updating its details.

![Search and Update](screenshots/search-update.png)

### Low Stock Alert
Demonstrates identifying products with low available stock.

![Low Stock Alert](screenshots/low-stock.png)

## 9. Installation and Execution

### Requirements
- JDK 8
- MySQL Server
- MySQL Connector/J
- Visual Studio Code with Java support

### Steps

1. Clone or download this repository.
2. Open the project folder in VS Code.
3. Start MySQL Server.
4. Open MySQL Workbench and execute `inventory_db.sql`.
5. Ensure the MySQL Connector/J JAR is available in the `lib` folder.
6. Configure the database connection in `Main.java` with your local MySQL username and password.
7. Do not upload real passwords or other credentials to GitHub.
8. Run `Main.java` to launch the application.

### Database Connection

    jdbc:mysql://localhost:3306/inventory_db

Use your own local MySQL credentials. Never publish your database password.

## 10. Innovation and Key Contribution

The project combines basic inventory CRUD operations with a low-stock alert, helping users identify products that may need restocking.

## 11. SDG Alignment

**UN Sustainable Development Goal 9: Industry, Innovation and Infrastructure**

The project has an indirect connection with SDG 9 by demonstrating how digital technology can support efficient inventory management.

## 12. Outcome

The application provides a simple way to maintain product records, update stock information, search inventory, and identify low-stock products through a graphical interface connected to MySQL.

## 13. Future Scope

- User authentication and access control
- Export inventory reports to PDF or CSV
- Stock transaction history
- Barcode scanning
- Automatic low-stock notifications
- Improved reporting and analytics


.

## 14. Author

**Mahipal Singh**

Developed as an academic mini project for Advanced Java Programming Lab.
