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

-- Tabla: FamiliaArticulos
CREATE TABLE FamiliaArticulos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(40) NOT NULL UNIQUE, -- generar automaticamente
    denominacion VARCHAR(80) NOT NULL UNIQUE
);
-- Tabla: Articulos
CREATE TABLE Articulos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(40) NOT NULL UNIQUE, -- generar automaticamente
    codigo_barras VARCHAR(80), -- ignorar esto, no soy un supermercado
    descripcion VARCHAR(60),
    familia_id INT,
    iva_id INT NOT NULL,
    coste DECIMAL(10,2),
    margen DECIMAL(5,2),
    pvp DECIMAL(10,2),
    proveedor_id INT,
    stock DECIMAL(10,2),
    observaciones TEXT,
    FOREIGN KEY (iva_id) REFERENCES TiposIva(id),
    FOREIGN KEY (familia_id) REFERENCES FamiliaArticulos(id),
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
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    hash VARCHAR(128),
    qr VARCHAR(4296),
    cobrada BOOLEAN NOT NULL,
    forma_pago_id INT,
    fecha_cobro DATE,
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
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    observaciones TEXT,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id),
    FOREIGN KEY (iva_id) REFERENCES TiposIva(id)
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
    cantidad DECIMAL(10,2) NOT NULL,
    descripcion VARCHAR(80),
    codigo VARCHAR(50) NOT NULL, -- se refiere al codigo del articulo
    precio_unitario DECIMAL(10,2) NOT NULL,
    iva DECIMAL(10,2) NOT NULL,
    base_imponible DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (factura_id) REFERENCES FacturasClientes(id),
    FOREIGN KEY (articulo_id) REFERENCES Articulos(id)
);

-- Tabla: LineasRectificativasClientes
CREATE TABLE LineasRectificativa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rectificativa_id INT NOT NULL,
    articulo_id INT NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    descripcion VARCHAR(80),
    codigo VARCHAR(50) NOT NULL, -- se refiere al codigo del articulo
    precio_unitario DECIMAL(10,2) NOT NULL,
    base_imponible DECIMAL(10,2) NOT NULL,
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (rectificativa_id) REFERENCES RectificativasClientes(id),
    FOREIGN KEY (articulo_id) REFERENCES Articulos(id)
);
