-- Create productos table
CREATE TABLE productos (
    id UUID PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INTEGER NOT NULL,
    categoria VARCHAR(50),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP NOT NULL
);

-- Create index on codigo for faster lookups
CREATE INDEX idx_productos_codigo ON productos(codigo);

-- Create index on categoria for filtering
CREATE INDEX idx_productos_categoria ON productos(categoria);

-- Create index on activo for filtering active products
CREATE INDEX idx_productos_activo ON productos(activo);
