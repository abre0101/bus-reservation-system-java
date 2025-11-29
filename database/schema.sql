-- Bus Reservation System Database Schema
-- Database: ticket_db

CREATE DATABASE IF NOT EXISTS ticket_db;
USE ticket_db;

-- User table
CREATE TABLE IF NOT EXISTS user (
    uid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    Status VARCHAR(20) DEFAULT 'Active',
    User_Type VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_name (name),
    INDEX idx_status (Status)
);

-- Bus table
CREATE TABLE IF NOT EXISTS bus (
    bus_id INT AUTO_INCREMENT PRIMARY KEY,
    busname VARCHAR(100) NOT NULL UNIQUE,
    available_seats INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_busname (busname)
);

-- Route table
CREATE TABLE IF NOT EXISTS route (
    route_id INT AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    distance INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_source_dest (source, destination)
);

-- Tariff table
CREATE TABLE IF NOT EXISTS tariff (
    tariff_id INT AUTO_INCREMENT PRIMARY KEY,
    route_id INT,
    price_per_km DECIMAL(10,2) NOT NULL,
    effective_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (route_id) REFERENCES route(route_id) ON DELETE CASCADE
);

-- Booking table
CREATE TABLE IF NOT EXISTS booking (
    bid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    busname VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    distination VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    numTicket INT NOT NULL,
    km INT NOT NULL,
    totalprice DECIMAL(10,2) NOT NULL,
    seatsAvailable INT NOT NULL,
    DATE_OF_BOOKING TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_busname (busname),
    INDEX idx_date (date),
    INDEX idx_booking_date (DATE_OF_BOOKING)
);

-- Payments table
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    bid INT NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'completed',
    FOREIGN KEY (bid) REFERENCES booking(bid) ON DELETE CASCADE,
    INDEX idx_bid (bid)
);

-- Support requests table
CREATE TABLE IF NOT EXISTS support_requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    subject VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_status (status)
);

-- Support responses table
CREATE TABLE IF NOT EXISTS support_responses (
    response_id INT AUTO_INCREMENT PRIMARY KEY,
    request_id INT NOT NULL,
    response_text TEXT NOT NULL,
    responded_by VARCHAR(100) NOT NULL,
    response_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (request_id) REFERENCES support_requests(request_id) ON DELETE CASCADE
);

-- Insert default admin user (password: admin123)
-- Note: This is encrypted using the application's encryption method
INSERT INTO user (name, password, phone, Status, User_Type) 
VALUES ('admin', 'ENCRYPTED_PASSWORD_HERE', '1234567890', 'Active', 'Admin')
ON DUPLICATE KEY UPDATE name=name;
