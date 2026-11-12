# be-ecommerce-lariosanderson

Backend API REST E-commerce implementado con **Spring Boot 3**, **Spring Data JPA**, **Spring Security + JWT Stateless**, **CORS**, **Springdoc OpenAPI (Swagger)**, **Docker** (MySQL 8.0) y soporte de despliegue en la nube en **Render (PostgreSQL)**.

- **Autor:** Anderson Fabrizzio Larios Tito
- **Base de datos:** `db_ecommerce_lariosanderson`
- **Tabla principal:** `productos` (`id`, `nombre`, `categoria`, `precio_compra`, `precio_venta`, `stock`, `marca`, `codigo_barras`, `fecha_ingreso`)
- **Tabla seguridad:** `usuarios` (`id`, `username`, `password`, `nombre_completo`, `rol`, `activo`)

## Usuarios Iniciales (JWT)
| Usuario | Contraseña | Rol |
|---|---|---|
| `anderson` | `anderson123` | `ROLE_ADMIN` |
| `admin` | `admin123` | `ROLE_ADMIN` |
| `invitado` | `invitado123` | `ROLE_USER` |

## Endpoints Principales
- `POST /api/auth/login` - Autenticacion JWT Stateless
- `POST /api/auth/register` - Registro de usuario
- `GET /api/productos` - Listar todos los productos (`@NamedQuery`)
- `GET /api/productos/{id}` - Obtener producto por ID
- `POST /api/productos` - Crear producto (requiere `ROLE_ADMIN`)
- `PUT /api/productos/{id}` - Actualizar producto (requiere `ROLE_ADMIN`)
- `DELETE /api/productos/{id}` - Eliminar producto (requiere `ROLE_ADMIN`)
- `GET /api/productos/categoria/{categoria}` - Buscar por categoria (`@NamedQuery` segura)
- `GET /api/productos/buscar?nombre=...` - Buscar por nombre (`@NamedQuery` segura)
- `GET /api/productos/stock-bajo?minimo=10` - Filtrar stock bajo (`@NamedQuery` segura)
- `GET /swagger-ui.html` - Documentacion interactiva OpenAPI / Swagger
