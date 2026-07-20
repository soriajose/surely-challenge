# Surely Challenge — E-Commerce Carrito de Compras & API REST

![Java 17](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-brightgreen?style=for-the-badge&logo=springboot)
![Angular](https://img.shields.io/badge/Angular-18-red?style=for-the-badge&logo=angular)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Architecture](https://img.shields.io/badge/Architecture-Hexagonal-purple?style=for-the-badge)

Sistema integral de gestión de carritos de compras e-commerce desarrollado como solución a la evaluación técnica. Implementa un motor dinámico de promociones y descuentos según el tipo de cliente y fecha, gestión del ciclo de vida del carrito (creación, edición, eliminación lógica, checkout/finalización de compra) y reportes de historial de consumo.

---

## 🛠️ Tecnologías Utilizadas

### Backend
* **Java 17**: Lenguaje de programación base.
* **Spring Boot 4.1.0**: Framework principal para el desarrollo de la API REST.
* **Spring Data JPA / Hibernate**: Persistencia relacional con mapeo Objeto-Relacional.
* **MySQL 8.0**: Base de datos relacional.
* **Maven**: Construcción multimódulo (`core` para reglas de dominio puras y `adapter` para infraestructura/web).
* **Springdoc OpenAPI 3 (v3.0.3)**: Documentación interactiva e interfaz visual de Swagger UI.
* **Lombok**: Reducción de código repetitivo (Getters/Setters/Constructores).

### Frontend
* **Angular 18**: Framework SPA (Single Page Application) moderno.
* **TypeScript**: Lenguaje tipado superset de JavaScript.
* **Angular Signals & RxJS**: Gestión reactiva de estado de componentes sin mutación directa.
* **Reactive Forms (`FormBuilder`, `FormGroup`)**: Formularios y validaciones asíncronas.
* **SCSS & CSS Custom Properties**: Sistema de diseño basado en Design Tokens (estilos responsivos y dark-mode nativo).

### Arquitectura & Patrones de Diseño
* **Arquitectura Hexagonal (Puertos y Adaptadores)**: Aislamiento total del núcleo de negocio (`core`) de los frameworks de persistencia y web (`adapter`).
* **Domain-Driven Design (DDD)**: Entidades ricas de dominio (`Cart`, `CartItem`, `User`, `Product`) que controlan su propio estado.
* **Strategy Pattern**: Cálculo dinámico de totales y aplicación de promociones según el tipo de carrito (`NormalPricingStrategy`, `SpecialDatePricingStrategy`, `VipPricingStrategy`).
* **Factory Pattern**: Creación encapsulada de carritos e ítems.

---

## 🚀 Guía de Levantado Rápido (Paso a Paso)

### Requisitos Previos
* **Java 17** instalado (`java -version`).
* **MySQL 8.0** en ejecución en el puerto `3306` (usuario `root`, contraseña `root`).
* **Node.js 18+** y **npm** instalados.

---

### Paso 1: Clonar el Repositorio
```bash
git clone <URL_DEL_REPOSOTORIO>
cd surely-challenge
```

---

### Paso 2: Inicializar la Base de Datos (MySQL)
Ejecutá el script de inicialización para crear la base de datos `surelydb`, las tablas y poblar los datos de prueba (usuarios normales, clientes VIP, productos y compras pasadas):

* **Desde línea de comandos**:
  ```bash
  mysql -u root -p < surely-challenge-backend/scripts/schema-and-data-mysql.sql
  ```
* **O desde tu cliente SQL preferido (Workbench, DBeaver, etc.)**:  
  Abrí y ejecutá el archivo `surely-challenge-backend/scripts/schema-and-data-mysql.sql`.

---

### Paso 3: Levantar el Backend (Spring Boot)
```bash
cd surely-challenge-backend
mvn spring-boot:run
```
El servidor backend se iniciará en `http://localhost:8080`.

---

### Paso 4: Levantar el Frontend (Angular)
En una nueva terminal:
```bash
cd surely-challenge-frontend
npm install
npm start
```
La aplicación web estará disponible en `http://localhost:4200`.

---

## 📖 Documentación Interactiva de la API (Swagger UI)

Una vez iniciado el backend, podés explorar y probar todos los endpoints REST directamente desde la interfaz web interactiva de Swagger:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---

## 🧪 Tabla de Usuarios de Prueba (Seed Data)

El script de inicialización (`schema-and-data-mysql.sql`) incluye clientes precargados diseñados específicamente para probar cada regla de negocio y funcionalidad del sistema:

| DNI Cliente | Nombre | Tipo de Cliente | Escenario / Prueba que Valida |
| :--- | :--- | :--- | :--- |
| **`30412567`** | Juan Pérez | **Común / Normal** | • **Prueba Descuento 10%**: Tiene un carrito abierto (`OPEN`) con 12 productos precargados.<br>• **Prueba Reporte Top 4**: Posee un carrito en estado `CLOSED` con compras pasadas de alto valor. |
| **`28904511`** | Lucía Fernández | **Cliente VIP** | • **Prueba Beneficios VIP**: Al crear o consultar su carrito, el sistema le asigna automáticamente la estrategia VIP (bonifica el producto más barato + $700 de descuento si tiene >5 ítems).<br>• **Prueba Historial VIP**: Posee un carrito `CLOSED`. |
| **`35678123`** | Martín Gómez | **Común / Normal** | • **Prueba Creación desde Cero**: Usuario registrado sin carritos activos. Ideal para probar el flujo de "Crear Carrito" y agregar productos manualmente. |
| **`40123456`** | Sofía Ramírez | **Cliente VIP** | • **Prueba Asignación Automática VIP**: Usuario VIP sin carritos iniciales. Permite verificar que al ingresar su DNI se genere directamente un carrito VIP vaciador. |

---

## 📋 Reglas de Negocio Implementadas

1. **Tipos de Carrito y Prioridad**:
   - Existen 3 tipos de carritos: **Común**, **Promocional por Fecha Especial** y **Promocional VIP**.
   - No son combinables. El beneficio VIP tiene prioridad si el usuario posee dicho atributo.
2. **Promociones**:
   - **10 o más productos**: 10% de descuento sobre el total.
   - **Más de 5 productos en Fecha Especial**: Descuento fijo de $500.
   - **Más de 5 productos en Cliente VIP**: Producto más barato gratis + $700 de descuento general.
3. **Ciclo de Vida e Inmutabilidad**:
   - Los carritos en estado `CLOSED` (finalizados) o `CANCELED` (eliminados) pasan a **Modo Read-Only**, impidiendo cualquier modificación posterior.
4. **Reporte Top 4**:
   - Filtra estrictamente por carritos en estado `CLOSED` del historial del cliente.

---

## ✒️ Autor
* **Jose Soria** — *Desarrollador Backend Java*
