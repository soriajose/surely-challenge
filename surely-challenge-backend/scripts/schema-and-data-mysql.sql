-- =========================================================
-- Surely Challenge - MySQL Init Script
--
-- Incluye la creación del esquema completo (tablas) y
-- los inserts de prueba para distintos escenarios.
-- =========================================================

CREATE DATABASE IF NOT EXISTS surelydb
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE surelydb;

-- =========================================================
-- 1) CREACIÓN DE TABLAS
-- =========================================================

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    document_number VARCHAR(50) UNIQUE,
    email VARCHAR(50),
    is_vip BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATE,
    created_by VARCHAR(50),
    updated_at DATE,
    updated_by VARCHAR(50),
    deleted_at DATE,
    deleted_by VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(50),
    price DECIMAL(19, 2),
    created_at DATE,
    created_by VARCHAR(50),
    updated_at DATE,
    updated_by VARCHAR(50),
    deleted_at DATE,
    deleted_by VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    strategy_name VARCHAR(50),
    status VARCHAR(50),
    created_at DATE,
    created_by VARCHAR(50),
    updated_at DATE,
    updated_by VARCHAR(50),
    deleted_at DATE,
    deleted_by VARCHAR(50),
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL DEFAULT 1,
    created_at DATE,
    created_by VARCHAR(50),
    updated_at DATE,
    updated_by VARCHAR(50),
    deleted_at DATE,
    deleted_by VARCHAR(50),
    CONSTRAINT fk_cartitem_cart FOREIGN KEY (cart_id) REFERENCES carts(id),
    CONSTRAINT fk_cartitem_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS promotional_dates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    created_at DATE,
    created_by VARCHAR(50),
    updated_at DATE,
    updated_by VARCHAR(50),
    deleted_at DATE,
    deleted_by VARCHAR(50)
);

-- =========================================================
-- 2) INSERTS: USUARIOS
-- =========================================================
INSERT INTO users (first_name, last_name, document_number, email, is_vip, created_at, created_by) VALUES
('Juan', 'Pérez', '30412567', 'juan.perez@mail.com', false, CURDATE(), 'admin@mail.com'),
('Lucía', 'Fernández', '28904511', 'lucia.fernandez@mail.com', true, CURDATE(), 'admin@mail.com'),
('Martín', 'Gómez', '35678123', 'martin.gomez@mail.com', false, CURDATE(), 'admin@mail.com'),
('Sofía', 'Ramírez', '40123456', 'sofia.ramirez@mail.com', true, CURDATE(), 'admin@mail.com'),
('Diego', 'Torres', '32567890', 'diego.torres@mail.com', false, CURDATE(), 'admin@mail.com');

-- =========================================================
-- 3) INSERTS: PRODUCTOS
-- =========================================================
INSERT INTO products (name, description, price, created_at, created_by) VALUES
('Notebook Lenovo IdeaPad 15"', 'Laptop 8GB RAM, 256GB SSD', 850000.00, CURDATE(), 'admin@mail.com'),
('Monitor LG 24" Full HD', 'Monitor IPS 75Hz', 210000.00, CURDATE(), 'admin@mail.com'),
('Auriculares Sony WH-1000XM4', 'Auriculares inalámbricos con cancelación de ruido', 380000.00, CURDATE(), 'admin@mail.com'),
('Silla Gamer Cougar', 'Silla ergonómica reclinable', 320000.00, CURDATE(), 'admin@mail.com'),
('Teclado Mecánico Redragon', 'Teclado RGB switches rojos', 45000.00, CURDATE(), 'admin@mail.com'),
('Mouse Logitech G203', 'Mouse óptico gaming', 22000.00, CURDATE(), 'admin@mail.com'),
('Webcam Logitech C920', 'Webcam Full HD 1080p', 65000.00, CURDATE(), 'admin@mail.com'),
('Disco SSD Kingston 480GB', 'Unidad de estado sólido SATA', 58000.00, CURDATE(), 'admin@mail.com'),
('Memoria RAM Corsair 16GB', 'Módulo DDR4 3200MHz', 72000.00, CURDATE(), 'admin@mail.com'),
('Router TP-Link Archer C6', 'Router WiFi doble banda', 38000.00, CURDATE(), 'admin@mail.com'),
('Impresora HP DeskJet', 'Impresora multifunción a color', 145000.00, CURDATE(), 'admin@mail.com'),
('Parlante JBL Flip 6', 'Parlante bluetooth portátil', 130000.00, CURDATE(), 'admin@mail.com'),
('Cargador Anker 65W', 'Cargador USB-C GaN', 28000.00, CURDATE(), 'admin@mail.com'),
('Mochila Targus 15"', 'Mochila para notebook', 35000.00, CURDATE(), 'admin@mail.com'),
('Pendrive Sandisk 128GB', 'Unidad flash USB 3.0', 15000.00, CURDATE(), 'admin@mail.com'),
('Smartwatch Xiaomi Band 8', 'Pulsera inteligente', 42000.00, CURDATE(), 'admin@mail.com');

-- =========================================================
-- 4) INSERTS: ESCENARIOS DE PRUEBA (Carts)
-- =========================================================

-- ESCENARIO 1: Juan Pérez (Común). Más de 10 items (descuento 100/200).
INSERT INTO carts (user_id, strategy_name, status, created_at, created_by)
SELECT id, 'NormalPricingStrategy', 'OPEN', CURDATE(), 'admin@mail.com'
FROM users WHERE document_number = '30412567';
SET @cart_juan_1 = LAST_INSERT_ID();

INSERT INTO cart_items (cart_id, product_id, quantity, created_at, created_by)
SELECT @cart_juan_1, id, 1, CURDATE(), 'admin@mail.com' FROM products LIMIT 12;

-- ESCENARIO 2: Lucía Fernández (VIP). Comprando más de 5 productos (descuento VIP del producto más barato).
INSERT INTO carts (user_id, strategy_name, status, created_at, created_by)
SELECT id, 'VipPricingStrategy', 'OPEN', CURDATE(), 'admin@mail.com'
FROM users WHERE document_number = '28904511';
SET @cart_lucia_1 = LAST_INSERT_ID();

INSERT INTO cart_items (cart_id, product_id, quantity, created_at, created_by)
SELECT @cart_lucia_1, id, 1, CURDATE(), 'admin@mail.com' FROM products WHERE name IN ('Impresora HP DeskJet', 'Parlante JBL Flip 6', 'Memoria RAM Corsair 16GB', 'Webcam Logitech C920', 'Disco SSD Kingston 480GB', 'Router TP-Link Archer C6');

-- ESCENARIO 3: Juan Pérez (Común). Compra FINALIZADA (status CLOSED).
-- Necesario para que el reporte de "4 productos más caros" devuelva datos:
-- la query solo considera carritos con status = 'CLOSED' (compras finalizadas).
INSERT INTO carts (user_id, strategy_name, status, created_at, created_by)
SELECT id, 'NormalPricingStrategy', 'CLOSED', CURDATE(), 'admin@mail.com'
FROM users WHERE document_number = '30412567';
SET @cart_juan_closed = LAST_INSERT_ID();

INSERT INTO cart_items (cart_id, product_id, quantity, created_at, created_by)
SELECT @cart_juan_closed, id, 1, CURDATE(), 'admin@mail.com'
FROM products WHERE name IN ('Notebook Lenovo IdeaPad 15"', 'Auriculares Sony WH-1000XM4', 'Silla Gamer Cougar', 'Monitor LG 24" Full HD', 'Teclado Mecánico Redragon');

-- ESCENARIO 4: Lucía Fernández (VIP). Compra FINALIZADA (status CLOSED),
-- para que su reporte también tenga historial.
INSERT INTO carts (user_id, strategy_name, status, created_at, created_by)
SELECT id, 'VipPricingStrategy', 'CLOSED', CURDATE(), 'admin@mail.com'
FROM users WHERE document_number = '28904511';
SET @cart_lucia_closed = LAST_INSERT_ID();

INSERT INTO cart_items (cart_id, product_id, quantity, created_at, created_by)
SELECT @cart_lucia_closed, id, 1, CURDATE(), 'admin@mail.com'
FROM products WHERE name IN ('Impresora HP DeskJet', 'Parlante JBL Flip 6', 'Smartwatch Xiaomi Band 8');

-- =========================================================
-- 5) FECHA PROMOCIONAL
--    Activa hoy como fecha promocional para testear descuentos
--    extra en carritos normales.
-- =========================================================
INSERT INTO promotional_dates (date, created_at, created_by) VALUES (CURDATE(), CURDATE(), 'admin@mail.com');
