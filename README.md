# user_preferences_api

Microservicio para la gestión de preferencias de los usuarios (gustos, intereses y formularios relacionados).

## 📌 Descripción

`user_preferences_api` es un microservicio responsable de registrar, actualizar y consultar las **preferencias y gustos** de los usuarios dentro del ecosistema. Estas preferencias permiten personalizar la experiencia del turista o proveedor, recomendando servicios acordes a sus intereses.

Sus funcionalidades incluyen:

- Registro y edición de intereses del usuario.
- Consulta de preferencias por usuario.
- Relación de intereses con servicios turísticos.
- Envío de eventos a otros microservicios mediante colas de mensajería.

Este microservicio forma parte de una arquitectura basada en microservicios enfocada en el ecosistema de turismo de bienestar.

## 🚀 Tecnologías

- Java 17  
- Spring Boot  
- Spring Web  
- Firebase Authentication / JWT  
- Maven  
- SQLite 
- RabbitMQ  

## 📡 Endpoints principales

### 📘 Gestión de Preferencias

- `POST /preferencias/{userId}` → Registrar o actualizar las preferencias de un usuario  
- `GET /preferencias/{userId}` → Obtener preferencias por ID de usuario  
- `GET /preferencias` → Listar todas las preferencias registradas  
- `DELETE /preferencias/{userId}` → Eliminar preferencias de un usuario  

### 📘 Búsqueda de Servicios

- `POST /buscar_servicio/{userId}?user={userName}` → Buscar servicios basados en las preferencias del usuario

## 🛡️ Seguridad

Este microservicio utiliza autenticación basada en JWT o Firebase Authentication para proteger sus endpoints.  
Los accesos se controlan según el rol del usuario (`ROLE_TURISTA`, `ROLE_ADMIN`, etc.).

## ⚙️ Para su funcionamiento

Este microservicio debe ejecutarse junto con los siguientes componentes del sistema:

- **Frontend** [`FrontEnd_IWellness`](https://github.com/IWellnessTesis/FrontEnd_IWellness).
- **Microservicio de gestión de usuarios** [`admin_users_api`](https://github.com/IWellnessTesis/IWellness_data_services/tree/main).
- **Microservicio de gestión de servicios** [`providers_api`](https://github.com/IWellnessTesis/providers_api).

Además, para la publicación y procesamiento de mensajes, deben estar en funcionamiento los siguientes servicios:

- **Servidor de mensajería**   [`Queue_Rabbit`](https://github.com/IWellnessTesis/Queue-Rabbit).
- **Microservicio de procesamiento de datos**   [`IWellness_Data_Services`](https://github.com/IWellnessTesis/IWellness_data_services/tree/main).
- **Base de datos de persistencia (MySQL)** incluida en [`IWellness_DB`](https://github.com/IWellnessTesis/IWellness-DB).

## 🧪 Pruebas

- Test unitarios e integrados con JUnit + MockMvc.






