DROP TABLE IF EXISTS PRODUCTS;
DROP TABLE IF EXISTS CATEGORIES;

CREATE TABLE CATEGORIES (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE PRODUCTS (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    category_id INT,
    price DECIMAL(10, 2),
    stock_quantity INT,
    image_url VARCHAR(255),
    created_at DATE,
    updated_at DATE,
    FOREIGN KEY (category_id) REFERENCES CATEGORIES(id)
);