# Peluquería Al Corteccino

Aplicación de escritorio desarrollada en Java para la gestión integral de una peluquería, permitiendo administrar clientes, empleados, citas, servicios y facturación mediante una interfaz gráfica sencilla y una base de datos relacional.

## Características

- Gestión de clientes.
- Gestión de empleados.
- Gestión de perfiles de usuario.
- Gestión de servicios de peluquería.
- Gestión de citas.
- Gestión de facturas.
- Persistencia de datos mediante MariaDB/MySQL.
- Arquitectura MVC.
- Documentación generada mediante Javadoc.
- Control de versiones mediante Git y GitHub.

---

## Tecnologías utilizadas

- Java 21
- Eclipse IDE
- MariaDB / MySQL
- JDBC
- Git
- GitHub
- Javadoc

---

## Estructura del proyecto

```text
src/
│
├── controlador/
│   ├── ...
│
├── modelo/
│   ├── ...
│
├── vista/
│   ├── ...
│
└── Main.java
```

### Descripción de paquetes

| Paquete | Función |
|----------|----------|
| modelo | Entidades, acceso a datos y lógica de persistencia |
| vista | Interfaz gráfica de usuario |
| controlador | Comunicación entre modelo y vista |

---

## Base de datos

La aplicación utiliza una base de datos relacional diseñada para cubrir las necesidades básicas de una peluquería.

### Entidades principales

- Usuarios
- Clientes
- Personal
- Perfiles de Usuario
- Servicios
- Citas
- Facturas

### Ejemplo de perfiles

| Tipo | Descripción |
|--------|--------|
| Cliente | Cliente habitual |
| Cliente | Cliente VIP |
| Cliente | Cliente recomendado |
| Personal | Peluquero |
| Personal | Administrador |

---

## Requisitos

### Software

- JDK 21 o superior
- MariaDB 11+ o MySQL 8+
- Eclipse IDE (recomendado)

### Driver JDBC

Añadir el conector JDBC correspondiente al proyecto:

```java
com.mysql.cj.jdbc.Driver
```

Ejemplo de conexión:

```java
String url = "jdbc:mysql://localhost:3306/peluqueria";
String usuario = "usuario";
String password = "password";

Connection conexion =
    DriverManager.getConnection(url, usuario, password);
```

---

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/Mariokiller44/PeluqueriaAPPCorteccinoEscritorio.git
```

### 2. Importar en Eclipse

```text
File
 └─ Import
     └─ Existing Projects into Workspace
```

### 3. Crear la base de datos

Ejecutar los scripts SQL incluidos en:

```text
database/
```

### 4. Configurar credenciales

Modificar el fichero de configuración de conexión con los datos de tu servidor MariaDB.

### 5. Ejecutar la aplicación

```bash
Run As → Java Application
```

---

## Documentación

La documentación del proyecto puede generarse mediante Javadoc:

```bash
javadoc -d doc src/**/*.java
```

La documentación generada se almacenará en:

```text
doc/
```

Abrir:

```text
doc/index.html
```

en un navegador web.

---

## Control de versiones

Flujo de trabajo utilizado:

```bash
git checkout -b nueva-rama

git add .

git commit -m "Descripción de cambios"

git push origin nueva-rama
```

Posteriormente se crea una Pull Request en GitHub para fusionar los cambios.

---

## Estado actual del proyecto

### Completado

- Diseño inicial de la base de datos.
- Arquitectura MVC.
- Gestión de usuarios.
- Gestión de perfiles.
- Configuración GitHub.
- Generación de documentación Javadoc.

### En desarrollo

- Gestión avanzada de citas.
- Facturación.
- Validaciones.
- Mejoras de interfaz gráfica.
- Gestión de permisos por rol.

---

## Autor

Mario Escribano

Proyecto desarrollado con fines académicos y de aprendizaje para consolidar conocimientos en:

- Java
- Programación orientada a objetos
- Bases de datos relacionales
- JDBC
- Arquitectura MVC
- Git y GitHub
- Spring
- Spring Boot
