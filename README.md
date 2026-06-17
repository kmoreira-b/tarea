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

## Requisitos

- Java 17+ (probado con JDK 18)
- Docker Desktop (para PostgreSQL)

## 0. Configuracion inicial (IMPORTANTE)

Por seguridad, el archivo `application.properties` (que contiene la configuracion de la base de
datos y el secreto JWT) **no esta incluido en el repositorio**. Antes de correr el proyecto debe
crearlo a partir de la plantilla:

```bash
# desde la raiz del proyecto
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

En Windows (PowerShell):

```powershell
Copy-Item src/main/resources/application.properties.example src/main/resources/application.properties
```

El archivo de ejemplo ya trae valores funcionales (base de datos `postgres/postgres` y un
`jwt.secret` valido), por lo que puede correrlo tal cual. Si desea generar su propio `jwt.secret`
(debe ser Base64 que decodifique a 32 bytes o mas), puede usar:

```bash
openssl rand -base64 48
```

y pegar el resultado en `jwt.secret` dentro de `application.properties`.

## 1. Levantar PostgreSQL con Docker

```bash
docker compose -f docker/docker-compose.yml up -d
```

Esto levanta PostgreSQL 16 en `localhost:5432` (base de datos `postgres`, usuario `postgres`,
contraseña `postgres`).

## 2. Correr el proyecto

En Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

La aplicacion arranca en `http://localhost:8080`. Al iniciar, Hibernate crea las tablas y el
`DataInitializer` inserta los roles y los usuarios.

## Roles y usuarios precargados

| Usuario | Contraseña | Rol              |
|---------|------------|------------------|
| admin   | admin123   | SUPER-ADMIN-ROLE |
| user    | user123    | USER             |

La contraseña se guarda **encriptada en MD5** en la base de datos.

## Reglas de seguridad

- `POST /api/auth/login` → publico, devuelve un token JWT.
- `GET` de productos y categorias → cualquier usuario **autenticado** (admin o user).
- `POST`, `PUT`, `DELETE` de productos y categorias → solo **SUPER-ADMIN-ROLE**.

## Endpoints

| Metodo | Ruta                  | Descripcion          |
|--------|-----------------------|----------------------|
| POST   | /api/auth/login       | Login (obtener JWT)  |
| GET    | /api/categoria/       | Listar categorias    |
| GET    | /api/categoria/{id}   | Buscar categoria     |
| POST   | /api/categoria/       | Registrar categoria  |
| PUT    | /api/categoria/{id}   | Actualizar categoria |
| DELETE | /api/categoria/{id}   | Borrar categoria     |
| GET    | /api/producto/        | Listar productos     |
| GET    | /api/producto/{id}    | Buscar producto      |
| POST   | /api/producto/        | Registrar producto   |
| PUT    | /api/producto/{id}    | Actualizar producto  |
| DELETE | /api/producto/{id}    | Borrar producto      |

## Probar con Insomnia

1. Importar `insomnia_collection.json` en Insomnia.
2. Ejecutar **Login (SUPER-ADMIN-ROLE)** y copiar el valor `token` de la respuesta.
3. Pegar ese token en la variable de entorno `token` (Manage Environments → Base Environment).
4. Ejecutar las peticiones de Categorias y Productos. Las peticiones ya usan
   `Authorization: Bearer {{ token }}`.
5. Para comprobar los permisos, repetir con **Login (USER)**: los `GET` funcionan (200), pero
   `POST`/`PUT`/`DELETE` devuelven **403 Forbidden**.
