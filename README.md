
# 🛡️ CRUD de Usuarios con Seguridad JWT

Este proyecto es una API REST de gestión de usuarios desarrollada con **Spring Boot 3** y **Java 21**, que implementa seguridad basada en **JWT** y 
control de acceso por **roles**. Incluye endpoints protegidos según el rol del usuario (ADMIN o USER).

📁 La base de datos está configurada con **H2 en memoria** para pruebas rápidas, aunque está preparada para conectarse fácilmente a **MySQL**.

---

## 💻 Tecnologías utilizadas

- 🧩 **Java 21**
- 🌱 **Spring Boot 3.2.2**
- 🛡️ **Spring Security**
- 🗝️ **JWT**
- 🗃️ **H2 Database** (modo prueba)
- 🐬 **MySQL** (opcional)
- 🔍 **Spring Data JPA**
- 🧪 **JUnit**
- 🧱 **Mockito**
- 📘 **Swagger para documentación**

---

## 👥 Usuarios de prueba

| Rol        | Email             | Contraseña | Username |
|------------|-------------------|------------|----------|
| 👑 ADMIN   | admin@email.com   | admin123   | Admin    |
| 🙋 USER    | user@email.com    | user123    | User     |

Estos usuarios se cargan automáticamente al iniciar la aplicación para facilitar las pruebas.

---

## 🔐 Endpoints protegidos por rol

Dependiendo del rol, los usuarios pueden acceder a diferentes rutas:

- `GET /showAll` → solo **ADMIN**
- `DELETE /delete/{username}` → solo **ADMIN**
- `GET /get/{username}` → solo **USER**
- `PUT /update/{username}` → solo **USER**
- `POST /log-in` → acceso libre
- `POST /register` → acceso libre

---

## 🧭 Documentación interactiva (Swagger UI)

🔹 [Acceder a Swagger UI](http://localhost:8080/swagger-ui/index.html)  
Desde esta interfaz podés probar todos los endpoints disponibles.

### 🔑 Cómo probar con Swagger

1. Registrate con `/register` usando el formulario de Swagger.
2. Luego, logueate desde `/log-in` con el mismo usuario.
3. Copiá el token JWT **sin las comillas**.
4. Hacé clic en el botón **"Authorize"** (arriba a la derecha).
5. Pegá el token directamente en el campo (sin comillas ni prefijo).

---

## 🗄️ Consola de Base de Datos H2

🔹 [Acceder a H2 Console](http://localhost:8080/h2-console)  
**JDBC URL:** `jdbc:h2:mem:testdb`  
**Usuario:** `sa`  
**Contraseña:** *(dejar en blanco)*

---

## ⚙️ Cómo ejecutar el proyecto

1. Cloná el repositorio:
```bash
git clone https://github.com/tuusuario/tu-repo.git
```

2. Abrilo con tu IDE favorito.

3. Ejecutá la clase principal:
```bash
com.tuempresa.tuprojecto.TuProyectoApplication
```

---

## ✅ Tests

El proyecto incluye pruebas unitarias y de integración con **JUnit** y **Mockito** para asegurar el correcto funcionamiento de los servicios principales.

---

## 📝 Notas

- En producción, podés cambiar la base de datos H2 por **MySQL** desde el archivo `application.properties`.
- La arquitectura permite agregar fácilmente más roles y niveles de acceso.
- Ideal como base para cualquier sistema que requiera autenticación robusta.

---

## 📬 Contacto

¿Te gustó el proyecto?  
📨 ¡Podés escribirme por [LinkedIn](https://www.linkedin.com/in/daniel-iwach/)!  
