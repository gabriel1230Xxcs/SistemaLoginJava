# 🔐 SistemaLoginJava

Sistema de login desarrollado en **Java** con conexión a **MySQL** y una interfaz gráfica moderna usando **Swing**.


##  Cómo ejecutar el proyecto

1. Clonar o descargar el proyecto desde GitHub
2. Abrir el proyecto en **Eclipse**
3. Ejecutar la clase `Main.java`
4. Iniciar **XAMPP** (Apache y MySQL)
5. Importar la base de datos en **phpMyAdmin**
6. Verificar que la conexión en `Conexion.java` esté correcta


##  Base de datos

* Nombre de la base de datos: `login_java`
* Tabla: `usuarios`

Estructura básica:

* id (INT, AUTO_INCREMENT, PRIMARY KEY)
* usuario (VARCHAR)
* password (VARCHAR)
* nombre (VARCHAR)
* apellido (VARCHAR)
* telefono (VARCHAR)
* correo (VARCHAR)


⚙️ Tecnologías usadas

* ☕ Java
* 🗄️ MySQL
* 🖥️ Eclipse
* 🔥 XAMPP
* 🎨 Swing (Interfaz gráfica)

---
 Funcionalidades

* 🔐 Inicio de sesión
* 👤 Registro de usuarios
* 📋 Visualización de datos
* ✏️ Edición de usuarios
* ❌ Eliminación de usuarios


##  Notas

* La contraseña se maneja en texto plano (sin encriptación)
* Asegúrate de que MySQL esté activo antes de ejecutar


##  Autor

**Gabriel Rodríguez**
