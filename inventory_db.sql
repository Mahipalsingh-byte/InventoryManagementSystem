DROP DATABASE IF EXISTS inventory_db;

CREATE DATABASE inventory_db;

USE inventory_db;

CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL
);

INSERT INTO products
(product_id, product_name, quantity, price)
VALUES
(101, 'Keyboard', 25, 650.00),
(102, 'Mouse', 8, 350.00),
(103, 'Monitor', 5, 7500.00),
(104, 'Laptop Stand', 15, 900.00),
(105, 'USB Cable', 6, 150.00);

SELECT * FROM products;
