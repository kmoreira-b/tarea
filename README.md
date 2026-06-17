# Tarea CRUD - Producto y Categoria

API REST con Spring Boot para administrar **Productos** y **Categorias**, con autenticacion
**JWT**, **Spring Security**, roles y base de datos **PostgreSQL**.

## Arquitectura

Sigue la estructura vista en clase (referencia: proyecto veterinario):

```
controller/        Controladores REST (delgados)
service/           Logica de negocio
repository/        Spring Data JPA
mappers/           MapStruct (entidad <-> DTO)
models/entities/   Entidades JPA
models/dtos/       DTOs
security/          JWT, filtro, MD5 password encoder, UserDetails
config/            SecurityConfig (CORS) y DataInitializer (seed)
```

## Roles y usuarios precargados

| Usuario | Contraseña | Rol              |
|---------|------------|------------------|
| admin   | admin123   | SUPER-ADMIN-ROLE |
| user    | user123    | USER             |

La contraseña se guarda **encriptada en MD5** en la base de datos.

