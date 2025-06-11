create database akihabara_db;
use akihabara_db;

-- TABLA PRODUCTOS
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    precio DECIMAL(10,2),
    stock INT
);

-- TABLA CLIENTES
CREATE TABLE clientes (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
telefono VARCHAR(20),
fecha_registro DATE DEFAULT (CURRENT_DATE)
);

select * from productos;
select * from clientes;