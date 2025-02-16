DROP DATABASE IF EXISTS GESTION;

CREATE DATABASE IF NOT EXISTS GESTION;
USE GESTION;

-- Tabla: Clientes
CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cif VARCHAR(12) UNIQUE,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    direccion VARCHAR(80),
    cp VARCHAR(10),
    poblacion VARCHAR(80),
    provincia VARCHAR(60),
    pais VARCHAR(60),
    telefono VARCHAR(16),
    email VARCHAR(80),
    iban VARCHAR(40),
    riesgo DECIMAL(10,2),
    descuento DECIMAL(5,2),
    observaciones TEXT
);
-- Tabla: Proveedores
CREATE TABLE Proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cif VARCHAR(12) UNIQUE,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    direccion VARCHAR(80),
    cp VARCHAR(10),
    poblacion VARCHAR(80),
    provincia VARCHAR(60),
    pais VARCHAR(60),
    telefono VARCHAR(16),
    email VARCHAR(80),
    iban VARCHAR(40),
    observaciones TEXT
);

-- Tabla: Familias
CREATE TABLE Familias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(40) NOT NULL UNIQUE,
    denominacion VARCHAR(80) NOT NULL UNIQUE
);
-- Tabla: Articulos
CREATE TABLE Articulos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(40) NOT NULL UNIQUE,
    codigo_barras VARCHAR(80),
    descripcion VARCHAR(60),
    familia_id INT,
    coste DECIMAL(10,2),
    margen DECIMAL(5,2),
    pvp DECIMAL(10,2),
    proveedor_id INT,
    stock DECIMAL(10,2),
    observaciones TEXT,
    FOREIGN KEY (familia_id) REFERENCES Familias(id),
    FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id)
);

-- Tabla: FormaPago
CREATE TABLE FormaPago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(40) NOT NULL,
    observaciones TEXT
);

-- Tabla: FacturasClientes
CREATE TABLE FacturasClientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    fecha DATE NOT NULL,
    cliente_id INT NOT NULL,
    base_imponible DECIMAL(10,2) NOT NULL,
    iva DECIMAL(5,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    hash VARCHAR(128),
    qr VARCHAR(4296),
    cobrada BOOLEAN NOT NULL,
    forma_pago_id INT NOT NULL,
    fecha_cobro DATE NOT NULL,
    observaciones TEXT,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id),
    FOREIGN KEY (forma_pago_id) REFERENCES FormaPago(id)
);

-- Tabla: RectificativasClientes
CREATE TABLE RectificativasClientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    fecha DATE NOT NULL,
    cliente_id INT NOT NULL,
    base_imponible DECIMAL(10,2) NOT NULL,
    iva DECIMAL(5,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    hash VARCHAR(128),
    qr VARCHAR(4296),
    observaciones TEXT,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);
-- Tabla: TiposIVA
CREATE TABLE TiposIVA (
    id INT AUTO_INCREMENT PRIMARY KEY,
    iva DECIMAL(5,2) NOT NULL,
    observaciones TEXT
);

-- Tabla: LineasFacturasClientes
CREATE TABLE LineasFacturasClientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    factura_id INT NOT NULL,
    articulo_id INT NOT NULL,
    descripcion VARCHAR(80) NOT NULL,
    codigo VARCHAR(40) NOT NULL,
    pvp DECIMAL(10,2) NOT NULL,
    iva_id INT,
    FOREIGN KEY (factura_id) REFERENCES FacturasClientes(id),
    FOREIGN KEY (articulo_id) REFERENCES Articulos(id),
    FOREIGN KEY (iva_id) REFERENCES TiposIVA(id)
);