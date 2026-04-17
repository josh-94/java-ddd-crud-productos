# Java DDD CRUD - Productos y Clientes

API REST para gestión de Productos y Clientes implementada con **Domain-Driven Design (DDD)** en Java Spring Boot.

## 🚀 Ejecución con Docker

### Requisitos
- Docker
- Docker Compose

### Iniciar la aplicación

```bash
docker-compose up --build -d
```

Esto iniciará:
- **API REST**: http://localhost:8080
- **PostgreSQL**: puerto 5432
- **PgAdmin**: http://localhost:5050

### Ver logs

```bash
docker-compose logs -f app
```

### Detener la aplicación

```bash
docker-compose down
```

## 📚 API Endpoints

### Productos
- `GET` http://localhost:8080/api/productos - Listar todos
- `POST` http://localhost:8080/api/productos - Crear producto
- `GET` http://localhost:8080/api/productos/{id}` - Obtener por ID
- `PUT` http://localhost:8080/api/productos/{id}` - Actualizar
- `DELETE` http://localhost:8080/api/productos/{id}` - Eliminar

### Clientes
- `GET` http://localhost:8080/api/clientes - Listar todos
- `POST` http://localhost:8080/api/clientes - Crear cliente
- `GET` http://localhost:8080/api/clientes/{id}` - Obtener por ID
- `GET` http://localhost:8080/api/clientes/dni/{dni}` - Obtener por DNI
- `PUT` http://localhost:8080/api/clientes/{id}` - Actualizar
- `PATCH` http://localhost:8080/api/clientes/{id}/estado` - Cambiar estado
- `DELETE` http://localhost:8080/api/clientes/{id}` - Eliminar

## 🧪 Ejemplo de Uso

```bash
# Crear un cliente
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "dni": "12345678",
    "nombres": "Juan",
    "apellidos": "Pérez",
    "correo": "juan@email.com",
    "telefono": "987654321"
  }'

# Listar todos los clientes
curl http://localhost:8080/api/clientes
```

## 📖 Documentación Completa

Ver [CLIENTE_API_DOCUMENTATION.md](CLIENTE_API_DOCUMENTATION.md) para:
- Explicación detallada de la arquitectura DDD
- Documentación completa de todos los endpoints
- Ejemplos de uso con PowerShell
- Validaciones y códigos de error

## 🏗️ Arquitectura DDD

```
├── domain/              # Lógica de negocio
│   ├── model/          # Entidades del dominio
│   ├── repository/     # Interfaces (puertos)
│   └── service/        # Servicios de dominio
├── application/         # Casos de uso
│   └── usecase/        # Use cases
└── infrastructure/      # Detalles técnicos
    ├── persistence/    # Adaptadores de BD
    └── rest/           # Controladores REST
```

## 💾 Base de Datos

PostgreSQL con Flyway para migraciones:
- `V1__create_productos_table.sql`
- `V2__create_clientes_table.sql`

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3.2.3
- PostgreSQL 15
- Docker & Docker Compose
- Flyway
- MapStruct
- Lombok

## 📝 Acceso a PgAdmin

- URL: http://localhost:5050
- Email: `admin@admin.com`
- Password: `admin`

Configuración del servidor:
- Host: `postgres`
- Port: `5432`
- Database: `productos_db`
- Username: `postgres`
- Password: `postgres`
