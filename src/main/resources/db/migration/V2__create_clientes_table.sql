-- Create clientes table
CREATE TABLE clientes (
    id UUID PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP NOT NULL
);

-- Create index on DNI for faster lookups
CREATE INDEX idx_clientes_dni ON clientes(dni);

-- Create index on correo for faster lookups
CREATE INDEX idx_clientes_correo ON clientes(correo);

-- Create index on activo for filtering active clients
CREATE INDEX idx_clientes_activo ON clientes(activo);

-- Create index on apellidos for searching by last name
CREATE INDEX idx_clientes_apellidos ON clientes(apellidos);
