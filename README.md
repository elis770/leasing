# 🚗 Leasing - Sistema de Alquiler de Autos

Sistema backend de alquiler de autos desarrollado con **Spring Boot 4.0.1** y **Java 21**, enfocado en buenas prácticas de arquitectura MVC y relaciones JPA.

## 🏗️ Arquitectura

```
Controller → Service → Repository → Database
```

- **Controller**: Expone la API REST, recibe requests HTTP
- **Service**: Contiene la lógica de negocio y reglas del dominio
- **Repository**: Acceso a datos con Spring Data JPA
- **Frontend**: Solo consume la API, no filtra datos críticos

## 🧩 Entidades Principales

- **Owner**: Dueño de los autos
- **Car**: Auto disponible para alquiler
- **Client**: Usuario que alquila autos
- **Reservation**: Reserva de un auto por un cliente
- **Review**: Reseña que un cliente deja sobre un auto
- **User**: Autenticación (Spring Security)

## 🔗 Relaciones entre Entidades (JPA)

```
Owner 1 → * Car
Car * → 1 Owner
Car 1 → * Review
Client 1 → * Review
Car 1 → * Reservation
Client 1 → * Reservation
User 1 → 1 Owner/Client
```

## 🚀 Cómo Ejecutar

### Prerrequisitos
- Java 21
- Maven 3.8+

### Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Acceder a H2 Console

URL: `http://localhost:8080/h2-console`

**Credenciales:**
- JDBC URL: `jdbc:h2:mem:car_rental_db`
- Username: `sa`
- Password: _(vacío)_

## 📂 Estructura del Proyecto

```
src/main/java/com/example/leasing/
├── Entity/           # Entidades JPA (Car, Owner, Client, etc.)
├── Repository/       # Interfaces de acceso a datos
├── service/          # Lógica de negocio
├── controller/       # Controladores REST
├── dto/              # Objetos de transferencia de datos
├── exception/        # Manejo global de excepciones
└── config/           # Configuraciones (Security, CORS, etc.)
```

## 🗄️ Base de Datos

### Desarrollo
- **H2 Database** (en memoria)
- Se crea automáticamente al iniciar la aplicación
- Los datos se pierden al detener la aplicación

### Producción (futuro)
- **MySQL**
- Descomentar configuración en `application.properties`

## 📝 Logs

Los logs se guardan en:
- **Consola**: Salida estándar con colores
- **Archivo**: `logs/leasing-app.log`
  - Tamaño máximo por archivo: 10MB
  - Historial: 30 archivos
  - Tamaño total máximo: 100MB

## 🔐 Seguridad

- **Spring Security** configurado (básico por ahora)
- Usuario por defecto:
  - Username: `admin`
  - Password: `admin123`
- Todos los endpoints `/api/**` están abiertos temporalmente para desarrollo
- CORS habilitado para `localhost:3000`, `localhost:4200`, `localhost:5173`

## 🛠️ Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 4.0.1**
  - Spring Data JPA
  - Spring Security
  - Spring Web MVC
- **Hibernate 7.2.0**
- **H2 Database** (desarrollo)
- **MySQL Connector** (producción)
- **Lombok** (reducir boilerplate)
- **Maven** (gestión de dependencias)

## 📊 Estado del Proyecto

### ✅ Completado
- [x] Entidades JPA con relaciones correctas
- [x] Enums de estado (Car, Reservation, User)
- [x] Repository básico (ICarRepo con queries)
- [x] Sistema de excepciones global
- [x] Configuración de seguridad básica
- [x] Configuración de CORS
- [x] Configuración de logging

### ⏳ En Desarrollo
- [ ] Repositories restantes (Owner, Client, Reservation, Review, User)
- [ ] Services con lógica de negocio
- [ ] Controllers REST
- [ ] DTOs para transferencia de datos
- [ ] Autenticación JWT
- [ ] Tests unitarios y de integración

## 📌 Decisiones de Diseño

1. **Las búsquedas se implementan en el backend** (Repository), nunca en el frontend
2. **Lazy loading** en todas las relaciones para optimizar rendimiento
3. **@JsonManagedReference/@JsonBackReference** para evitar loops infinitos en JSON
4. **LocalDateTime** para fechas de reserva (permite elegir hora exacta)
5. **BCrypt** para encriptación de contraseñas
6. **Logs en archivo** para auditoría y debugging

## 👨‍💻 Desarrollador

Proyecto de práctica de Spring Boot enfocado en:
- Diseño de entidades y relaciones JPA
- Separación de responsabilidades (Repository vs Service)
- Construcción de API REST escalable y mantenible

---

**Última actualización:** Enero 2026
