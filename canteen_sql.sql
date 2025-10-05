-- Tạo database nếu chưa tồn tại
CREATE DATABASE IF NOT EXISTS canteen_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Sử dụng database
USE canteen_db;

-- Bảng User
CREATE TABLE IF NOT EXISTS User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    photo BLOB,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'staff', 'customer') DEFAULT 'customer', -- admin = quản trị, staff = quầy, customer = khách
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng Food
CREATE TABLE IF NOT EXISTS Food (
    id INT AUTO_INCREMENT PRIMARY KEY,
    image BLOB,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL DEFAULT 0.0,
    inventory INT NOT NULL DEFAULT 0
);

-- Bảng Orders
CREATE TABLE IF NOT EXISTS Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    totalPrice DOUBLE NOT NULL DEFAULT 0.0,
    status ENUM('new_order', 'confirmed', 'in_delivery', 'delivered') DEFAULT 'new_order',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES User(id)
        ON DELETE CASCADE
);

-- Bảng Order_Food (dùng để lưu list<Food> trong Order)
CREATE TABLE IF NOT EXISTS Order_Food (
    orderId INT NOT NULL,
    foodId INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    PRIMARY KEY (orderId, foodId),
    FOREIGN KEY (orderId) REFERENCES Orders(id)
        ON DELETE CASCADE,
    FOREIGN KEY (foodId) REFERENCES Food(id)
        ON DELETE CASCADE
);

-- Bảng Statistics (dùng để thống kê đơn hàng)
CREATE TABLE IF NOT EXISTS Statistics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stat_date DATE NOT NULL,
    staffId INT,
    foodId INT,
    orders_count INT DEFAULT 0,
    revenue DOUBLE DEFAULT 0.0,
    quantity_sold INT DEFAULT 0,
    UNIQUE(stat_date, staffId, foodId),
    FOREIGN KEY (staffId) REFERENCES User(id),
    FOREIGN KEY (foodId) REFERENCES Food(id)
);

