# 🛒 Tienda de Dispositivos Electrónicos - Backend

Backend para una tienda de dispositivos electrónicos desarrollado con Spring Boot.

## 🚀 Características

- **API REST** completa para gestión de dispositivos
- **Autenticación JWT** con roles diferenciados
- **Documentación Swagger/OpenAPI** integrada
- **Base de datos PostgreSQL**
- **Pruebas automatizadas** (JUnit)
- **Despliegue en Render**

## 🏗️ Tecnologías

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Security** con JWT
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger/OpenAPI 3**
- **Maven**
- **JUnit 5**

## 📋 Entidades Principales

- **Dispositivo**: Productos electrónicos (smartphones, laptops, etc.)
- **Usuario**: Sistema de usuarios con roles
- **Empresa**: Información de la empresa
- **Banner/Footer**: Gestión de contenido web

## 🔧 Instalación y Uso

### Prerrequisitos
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Configuración Local
1. Clona el repositorio
```bash
git clone https://github.com/MatiasVz/tienda-dispositivos-backend.git
cd tienda-dispositivos-backend
```

2. Configura la base de datos en `application.properties`
3. Ejecuta la aplicación
```bash
mvn spring-boot:run
```

4. Accede a Swagger UI: `http://localhost:8081/swagger-ui/index.html`

## 🧪 Pruebas

Ejecutar todas las pruebas:
```bash
mvn test
```

### Cobertura de Pruebas
- ✅ **Login**: 3 pruebas de autenticación
- ✅ **Validación**: Pruebas de validación de datos
- ✅ **CRUD**: 3 pruebas de operaciones CRUD

## 🌐 Despliegue

### Render (Producción)
- **URL**: [https://tu-app.onrender.com](https://tu-app.onrender.com)
- **Swagger**: [https://tu-app.onrender.com/swagger-ui/index.html](https://tu-app.onrender.com/swagger-ui/index.html)

### Local
- **Backend**: `http://localhost:8081`
- **Swagger**: `http://localhost:8081/swagger-ui/index.html`

## 📚 API Endpoints

### Autenticación
- `POST /auth/login` - Iniciar sesión
- `GET /auth/validate` - Validar token

### Dispositivos
- `GET /dispositivo` - Listar dispositivos
- `POST /dispositivo` - Crear dispositivo
- `GET /dispositivo/{id}` - Obtener dispositivo
- `PUT /dispositivo/{id}` - Actualizar dispositivo
- `DELETE /dispositivo/{id}` - Eliminar dispositivo

### Gestión
- `GET /empresa` - Información de empresa
- `GET /banner` - Gestión de banners
- `GET /footer` - Gestión de footer

## 🔒 Seguridad

- **JWT Authentication** para endpoints protegidos
- **Roles**: Admin, Usuario
- **CORS** configurado para frontend
- **Endpoints públicos**: GET de dispositivos, empresa, banners

## 👥 Autor

Desarrollado para proyecto académico de tienda de dispositivos electrónicos.

## 📄 Licencia

Este proyecto es de uso académico.
