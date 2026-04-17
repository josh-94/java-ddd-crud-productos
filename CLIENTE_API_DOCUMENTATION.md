# API REST - CRUD Cliente

## Resumen de la Implementación DDD

Este módulo implementa el **patrón Domain-Driven Design (DDD)** para la gestión de Clientes.

### Arquitectura en Capas

#### 1. **Capa de Dominio** (domain/)
Contiene la lógica de negocio pura, sin dependencias de frameworks:

- **`Cliente.java`** (Entidad de Dominio):
  - Modelo con campos: id, dni, nombres, apellidos, correo, telefono, activo, fechas
  - Métodos de negocio: `validar()`, `getNombreCompleto()`, `activar()`, `desactivar()`
  - Reglas de validación: DNI 8 dígitos, email válido, campos requeridos
  
- **`ClienteRepository.java`** (Puerto):
  - Interface que define qué operaciones necesita el dominio
  - No sabe cómo se implementa (puede ser JPA, MongoDB, etc.)
  
- **`ClienteService.java` y `ClienteServiceImpl.java`**:
  - Lógica de negocio compleja
  - Valida DNI y correo únicos antes de crear/actualizar
  - Orquesta operaciones del dominio

#### 2. **Capa de Aplicación** (application/)
Orquesta los casos de uso sin lógica de negocio:

- **`CreateClienteUseCase.java`**: Crear cliente
- **`GetClienteUseCase.java`**: Obtener cliente(s)
- **`UpdateClienteUseCase.java`**: Actualizar cliente
- **`DeleteClienteUseCase.java`**: Eliminar cliente

#### 3. **Capa de Infraestructura** (infrastructure/)
Implementa detalles técnicos y adapta tecnologías externas:

**Persistencia:**
- **`ClienteEntity.java`**: Entidad JPA con anotaciones de base de datos
- **`ClienteJpaRepository.java`**: Interface Spring Data JPA
- **`ClienteRepositoryAdapter.java`**: Implementa `ClienteRepository` (puerto del dominio)
- **`ClienteMapper.java`**: MapStruct para convertir Entity ↔ Domain

**REST:**
- **`ClienteController.java`**: Endpoints HTTP
- **`ClienteRequest.java`**: DTO para recibir datos
- **`ClienteResponse.java`**: DTO para enviar respuestas
- **`ClienteUpdateRequest.java`**: DTO para actualizaciones
- **`ClienteEstadoUpdateRequest.java`**: DTO para cambiar estado

---

## Base de Datos

**Tabla:** `clientes`

```sql
CREATE TABLE clientes (
    id UUID PRIMARY KEY,
    dni VARCHAR(8) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(15),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP NOT NULL
);
```

**Índices:**
- `idx_clientes_dni` (dni)
- `idx_clientes_correo` (correo)
- `idx_clientes_activo` (activo)
- `idx_clientes_apellidos` (apellidos)

---

## Endpoints de la API

### Base URL
```
http://localhost:8080/api/clientes
```

---

## 1. Crear Cliente

**POST** `/api/clientes`

**Request Body:**
```json
{
  "dni": "12345678",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "correo": "juan.perez@email.com",
  "telefono": "987654321",
  "activo": true
}
```

**Validaciones:**
- `dni`: Obligatorio, 8 dígitos numéricos, único
- `nombres`: Obligatorio, 2-100 caracteres
- `apellidos`: Obligatorio, 2-100 caracteres
- `correo`: Obligatorio, formato válido, único
- `telefono`: Opcional, máximo 15 caracteres
- `activo`: Opcional, por defecto `true`

**Response (201 Created):**
```json
{
  "id": "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3",
  "dni": "12345678",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "correo": "juan.perez@email.com",
  "telefono": "987654321",
  "activo": true,
  "fechaCreacion": "2026-04-17T22:30:15.123",
  "fechaActualizacion": "2026-04-17T22:30:15.123"
}
```

**PowerShell:**
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/clientes" `
  -Method POST `
  -Body '{"dni":"12345678","nombres":"Juan","apellidos":"Perez","correo":"juan@email.com","telefono":"987654321"}' `
  -ContentType "application/json"
```

**Errores Posibles:**
```json
{
  "status": 400,
  "message": "Ya existe un cliente con el DNI: 12345678"
}
```

```json
{
  "status": 400,
  "message": "Error de validación",
  "details": [
    "dni: El DNI debe tener 8 dígitos",
    "correo: El correo debe tener un formato válido"
  ]
}
```

---

## 2. Obtener Todos los Clientes

**GET** `/api/clientes`

**Query Parameters:**
- `activo` (opcional): `true` o `false` para filtrar por estado

**Response (200 OK):**
```json
[
  {
    "id": "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3",
    "dni": "12345678",
    "nombres": "Juan Carlos",
    "apellidos": "Pérez García",
    "correo": "juan.perez@email.com",
    "telefono": "987654321",
    "activo": true,
    "fechaCreacion": "2026-04-17T22:30:15.123",
    "fechaActualizacion": "2026-04-17T22:30:15.123"
  },
  {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "dni": "87654321",
    "nombres": "Maria",
    "apellidos": "Rodriguez",
    "correo": "maria@email.com",
    "telefono": "912345678",
    "activo": true,
    "fechaCreacion": "2026-04-17T22:31:20.456",
    "fechaActualizacion": "2026-04-17T22:31:20.456"
  }
]
```

**PowerShell:**
```powershell
# Todos los clientes
(Invoke-WebRequest -Uri "http://localhost:8080/api/clientes").Content | ConvertFrom-Json

# Solo clientes activos
(Invoke-WebRequest -Uri "http://localhost:8080/api/clientes?activo=true").Content | ConvertFrom-Json
```

---

## 3. Obtener Cliente por ID

**GET** `/api/clientes/{id}`

**Response (200 OK):**
```json
{
  "id": "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3",
  "dni": "12345678",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "correo": "juan.perez@email.com",
  "telefono": "987654321",
  "activo": true,
  "fechaCreacion": "2026-04-17T22:30:15.123",
  "fechaActualizacion": "2026-04-17T22:30:15.123"
}
```

**PowerShell:**
```powershell
$id = "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3"
(Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id").Content | ConvertFrom-Json
```

**Error (404 Not Found):**
```json
{
  "status": 404,
  "message": "Cliente no encontrado con id: 5df66a24-f8cd-49d3-ba52-c6a1292ab2a3"
}
```

---

## 4. Obtener Cliente por DNI

**GET** `/api/clientes/dni/{dni}`

**Response (200 OK):**
```json
{
  "id": "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3",
  "dni": "12345678",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "correo": "juan.perez@email.com",
  "telefono": "987654321",
  "activo": true,
  "fechaCreacion": "2026-04-17T22:30:15.123",
  "fechaActualizacion": "2026-04-17T22:30:15.123"
}
```

**PowerShell:**
```powershell
(Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/dni/12345678").Content | ConvertFrom-Json
```

---

## 5. Actualizar Cliente

**PUT** `/api/clientes/{id}`

**Request Body:**
```json
{
  "nombres": "Juan Carlos Actualizado",
  "apellidos": "Pérez García",
  "correo": "juan.nuevo@email.com",
  "telefono": "999888777"
}
```

**Notas:**
- Todos los campos son opcionales
- Solo se actualizan los campos enviados
- El DNI **NO** se puede actualizar
- El campo `activo` se actualiza con el endpoint PATCH

**Response (200 OK):**
```json
{
  "id": "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3",
  "dni": "12345678",
  "nombres": "Juan Carlos Actualizado",
  "apellidos": "Pérez García",
  "correo": "juan.nuevo@email.com",
  "telefono": "999888777",
  "activo": true,
  "fechaCreacion": "2026-04-17T22:30:15.123",
  "fechaActualizacion": "2026-04-17T22:35:00.789"
}
```

**PowerShell:**
```powershell
$id = "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3"
Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id" `
  -Method PUT `
  -Body '{"nombres":"Juan Actualizado","correo":"nuevo@email.com","telefono":"999888777"}' `
  -ContentType "application/json"
```

---

## 6. Cambiar Estado del Cliente

**PATCH** `/api/clientes/{id}/estado`

**Request Body:**
```json
{
  "activo": false
}
```

**Response (200 OK):**
```json
{
  "id": "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3",
  "dni": "12345678",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "correo": "juan.perez@email.com",
  "telefono": "987654321",
  "activo": false,
  "fechaCreacion": "2026-04-17T22:30:15.123",
  "fechaActualizacion": "2026-04-17T22:36:00.123"
}
```

**PowerShell:**
```powershell
# Desactivar cliente
$id = "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3"
Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id/estado" `
  -Method PATCH `
  -Body '{"activo":false}' `
  -ContentType "application/json"

# Reactivar cliente
Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id/estado" `
  -Method PATCH `
  -Body '{"activo":true}' `
  -ContentType "application/json"
```

---

## 7. Eliminar Cliente

**DELETE** `/api/clientes/{id}`

**Response (204 No Content):**
Sin contenido en el body

**PowerShell:**
```powershell
$id = "5df66a24-f8cd-49d3-ba52-c6a1292ab2a3"
Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id" -Method DELETE
```

**Error (404 Not Found):**
```json
{
  "status": 404,
  "message": "Cliente no encontrado con id: 5df66a24-f8cd-49d3-ba52-c6a1292ab2a3"
}
```

---

## Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 OK | Operación exitosa (GET, PUT, PATCH) |
| 201 Created | Cliente creado exitosamente (POST) |
| 204 No Content | Cliente eliminado exitosamente (DELETE) |
| 400 Bad Request | Error de validación o regla de negocio |
| 404 Not Found | Cliente no encontrado |
| 500 Internal Server Error | Error del servidor |

---

## Ejemplos de Errores de Validación

### DNI Inválido
```json
{
  "status": 400,
  "message": "Error de validación",
  "details": [
    "dni: El DNI debe contener solo números",
    "dni: El DNI debe tener 8 dígitos"
  ]
}
```

### Email Inválido
```json
{
  "status": 400,
  "message": "Error de validación",
  "details": [
    "correo: El correo debe tener un formato válido"
  ]
}
```

### Campos Requeridos
```json
{
  "status": 400,
  "message": "Error de validación",
  "details": [
    "dni: El DNI es obligatorio",
    "nombres: Los nombres son obligatorios",
    "apellidos: Los apellidos son obligatorios",
    "correo: El correo es obligatorio"
  ]
}
```

### DNI Duplicado
```json
{
  "status": 400,
  "message": "Ya existe un cliente con el DNI: 12345678"
}
```

### Correo Duplicado
```json
{
  "status": 400,
  "message": "Ya existe un cliente con el correo: juan@email.com"
}
```

---

## Flujo Completo de Prueba (PowerShell)

```powershell
# 1. Crear cliente
$response = Invoke-WebRequest -Uri "http://localhost:8080/api/clientes" `
  -Method POST `
  -Body '{"dni":"12345678","nombres":"Juan","apellidos":"Perez","correo":"juan@email.com","telefono":"987654321"}' `
  -ContentType "application/json"
$cliente = ($response.Content | ConvertFrom-Json)
$id = $cliente.id

# 2. Ver todos los clientes
(Invoke-WebRequest -Uri "http://localhost:8080/api/clientes").Content | ConvertFrom-Json

# 3. Obtener por ID
(Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id").Content | ConvertFrom-Json

# 4. Obtener por DNI
(Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/dni/12345678").Content | ConvertFrom-Json

# 5. Actualizar cliente
Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id" `
  -Method PUT `
  -Body '{"nombres":"Juan Actualizado","correo":"nuevo@email.com"}' `
  -ContentType "application/json"

# 6. Desactivar cliente
Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id/estado" `
  -Method PATCH `
  -Body '{"activo":false}' `
  -ContentType "application/json"

# 7. Filtrar solo activos
(Invoke-WebRequest -Uri "http://localhost:8080/api/clientes?activo=true").Content | ConvertFrom-Json

# 8. Eliminar cliente
Invoke-WebRequest -Uri "http://localhost:8080/api/clientes/$id" -Method DELETE
```

---

## Principios DDD Aplicados

### 1. **Separation of Concerns (Separación de Responsabilidades)**
- **Dominio**: No sabe de base de datos ni HTTP
- **Aplicación**: No tiene lógica de negocio
- **Infraestructura**: No toma decisiones de negocio

### 2. **Dependency Inversion (Inversión de Dependencias)**
- El dominio define `ClienteRepository` (puerto)
- La infraestructura implementa `ClienteRepositoryAdapter` (adaptador)
- El dominio no depende de detalles técnicos

### 3. **Rich Domain Model (Modelo de Dominio Rico)**
- `Cliente` tiene métodos de negocio: `validar()`, `activar()`, `desactivar()`
- No es un simple contenedor de datos (POJO)

### 4. **Use Cases (Casos de Uso)**
- Cada acción es un caso de uso independiente
- Facilita testing y mantenimiento

### 5. **Mappers (Conversión entre Capas)**
- `ClienteMapper` convierte entre Entity (JPA) y Domain Model
- Mantiene separación entre capas

---

## Ventajas de esta Arquitectura

1. **Testabilidad**: Cada capa se puede testear independientemente
2. **Mantenibilidad**: Cambios en una capa no afectan otras
3. **Flexibilidad**: Se puede cambiar la base de datos o framework sin tocar el dominio
4. **Claridad**: Cada clase tiene una responsabilidad única
5. **Escalabilidad**: Fácil agregar nuevos casos de uso

---

## Comparación con Arquitectura Tradicional

### Tradicional (Capas):
```
Controller → Service → Repository → Database
```

### DDD (Hexagonal):
```
REST (Adapter) → Application (Use Cases) → Domain (Business Logic)
                                              ↓
                                         Repository (Port)
                                              ↓
                                      JPA Adapter → Database
```

**Ventaja DDD**: El dominio es independiente de los detalles técnicos.
