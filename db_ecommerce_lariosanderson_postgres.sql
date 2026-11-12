DROP TABLE IF EXISTS productos;
CREATE TABLE productos (
    id             BIGSERIAL      PRIMARY KEY,
    nombre         VARCHAR(100)   NOT NULL,
    categoria      VARCHAR(50)    NOT NULL,
    precio_compra  NUMERIC(10,2)  NOT NULL,
    precio_venta   NUMERIC(10,2)  NOT NULL,
    stock          INTEGER        NOT NULL DEFAULT 0 CHECK (stock >= 0),
    marca          VARCHAR(50),
    codigo_barras  VARCHAR(50)    NOT NULL UNIQUE,
    fecha_ingreso  DATE           NOT NULL
);

DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios (
    id               BIGSERIAL     PRIMARY KEY,
    username         VARCHAR(50)   NOT NULL UNIQUE,
    password         VARCHAR(100)  NOT NULL,
    nombre_completo  VARCHAR(100)  NOT NULL,
    rol              VARCHAR(20)   NOT NULL,
    activo           BOOLEAN       NOT NULL DEFAULT TRUE
);

INSERT INTO productos (nombre, categoria, precio_compra, precio_venta, stock, marca, codigo_barras, fecha_ingreso) VALUES
('Laptop IdeaPad 3',        'Computo',     1800.00, 2299.90, 15, 'Lenovo',   '7751234000011', '2026-10-01'),
('Mouse inalambrico M185',  'Accesorios',    35.00,   59.90, 80, 'Logitech', '7751234000028', '2026-10-03'),
('Teclado mecanico K552',   'Accesorios',   120.00,  179.90, 40, 'Redragon', '7751234000035', '2026-10-05'),
('Monitor 24 pulgadas',     'Computo',      450.00,  649.90, 20, 'Samsung',  '7751234000042', '2026-10-08'),
('Audifonos WH-CH520',      'Audio',        150.00,  229.90,  8, 'Sony',     '7751234000059', '2026-10-10'),
('Smartphone Galaxy A15',   'Celulares',    600.00,  849.90, 25, 'Samsung',  '7751234000066', '2026-10-12');
