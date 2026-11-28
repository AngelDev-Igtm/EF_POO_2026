
# 🏨 INDIGO LOFT — Sistema de Gestión Hotelera

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![SQL Server](https://img.shields.io/badge/SQL_Server-2025-red)
![NetBeans](https://img.shields.io/badge/Apache_NetBeans-IDE-blue?logo=apachenetbeanside)
![Architecture](https://img.shields.io/badge/Arquitectura-MVC-green)
![Status](https://img.shields.io/badge/Estado-Desarrollado-success)

## 📖 Descripción del proyecto

Indigo Loft es un sistema de gestión hotelera desarrollado en Java, orientado a la automatización y administración de los procesos operativos de un hotel.

El sistema permite centralizar la información de huéspedes, empleados, departamentos, habitaciones, reservas y facturación mediante una interfaz gráfica y una base de datos relacional en Microsoft SQL Server.

El proyecto ha sido desarrollado aplicando los principios de la Programación Orientada a Objetos (POO), el patrón arquitectónico MVC y el patrón de acceso a datos DAO.

## 🎯 Objetivo general

Desarrollar un sistema informático que permita optimizar los procesos de gestión hotelera, garantizando la integridad de los datos y facilitando la administración de los recursos del establecimiento.

## ⚙️ Funcionalidades principales

- Inicio de sesión.
- Gestión de huéspedes.
- Gestión de empleados.
- Gestión de gerentes.
- Gestión de recepcionistas.
- Gestión de administrativos.
- Gestión de conserjes.
- Gestión de departamentos.
- Gestión de habitaciones simples y suites.
- Registro y administración de reservas.
- Validación de disponibilidad de habitaciones.
- Control de superposición de reservas.
- Registro de Check-in y Check-out.
- Cancelación de reservas.
- Generación de facturas.
- Generación de reportes.
- Persistencia de información en SQL Server.
- Validación de datos y manejo de excepciones.

## 💻 Tecnologías utilizadas

| Tecnología | Utilización |
|---|---|
| Java 25 | Lenguaje de programación |
| Java Swing | Interfaces gráficas |
| Apache NetBeans | Entorno de desarrollo |
| Apache Ant | Compilación del proyecto |
| Microsoft SQL Server | Motor de base de datos |
| SSMS | Administración de la base de datos |
| JDBC | Conexión entre Java y SQL Server |
| Git | Control de versiones |
| GitHub | Repositorio y colaboración |

## 🏗️ Arquitectura del sistema

El sistema utiliza el patrón Modelo-Vista-Controlador (MVC), complementado con el patrón DAO para separar la lógica de negocio del acceso a la base de datos.

| Componente | Responsabilidad |
|---|---|
| Modelo | Entidades, atributos y comportamiento del dominio |
| Vista | Interfaces gráficas e interacción con el usuario |
| Controlador | Coordinación entre la vista y el modelo |
| DAO | Consultas y operaciones sobre la base de datos |
| Conexión | Configuración y comunicación mediante JDBC |

### Principios de POO aplicados

- Abstracción.
- Encapsulamiento.
- Herencia.
- Polimorfismo.
- Asociación.
- Agregación.
- Composición.
- Sobrescritura de métodos.

## 📂 Estructura del proyecto

```text
EF_POO_2026/
│
├── src/
│   ├── Conexion/
│   ├── Controladores/
│   ├── DAO/
│   ├── Modelos/
│   ├── Vista/
│   └── primer_avance_poo/
│
├── Database/
│   └── SQLServer/
│       ├── 01_IndigoLoftDB.sql
│       └── 02_Configuracion_Usuario.sql
│
├── lib/
│   └── mssql-jdbc-13.6.0.jre11.jar
│
├── nbproject/
├── build.xml
├── manifest.mf
├── .gitignore
└── README.md
```

## 🗄️ Base de datos

**Nombre:** IndigoLoftDB

La base de datos está diseñada bajo el modelo relacional y utiliza restricciones de integridad para garantizar la consistencia de la información.

### Características

| Elemento | Cantidad |
|---|---:|
| Tablas | 14 |
| Claves primarias | 14 |
| Claves foráneas | 16 |
| Restricciones CHECK | 13 |
| Índices únicos adicionales | 3 |
| Restricciones DEFAULT | 3 |

### Tablas

1. PERSONA
2. EMPLEADO
3. HUESPED
4. GERENTE
5. ADMINISTRATIVO
6. CONSERJE
7. RECEPCIONISTA
8. DEPARTAMENTO
9. HABITACION
10. HABITACION_SIMPLE
11. SUITE
12. RESERVA
13. RESERVA_HABITACION
14. FACTURA

## 🚀 Guía de instalación

### 1. Requisitos previos

- Java Development Kit (JDK) 25.
- Apache NetBeans compatible con JDK 25.
- Microsoft SQL Server 2025.
- SQL Server Management Studio (SSMS).
- Git (opcional).
- Windows 10 o Windows 11.
- Puerto TCP 1433 disponible.

### 2. Descargar el proyecto

Clonar el repositorio mediante Git:

```bash
git clone https://github.com/AngelDev-Igtm/EF_POO_2026.git
```

También se puede descargar desde:

**Code → Download ZIP**

Descomprimir el archivo antes de abrir el proyecto en NetBeans.

### 3. Configurar SQL Server

Verificar que SQL Server esté instalado y que el servicio correspondiente se encuentre iniciado.

Desde SQL Server Configuration Manager:

1. Habilitar el protocolo TCP/IP.
2. Configurar el puerto TCP 1433.
3. Reiniciar el servicio de SQL Server.
4. Habilitar el modo de autenticación de SQL Server y Windows.

### 4. Crear la base de datos

Abrir SQL Server Management Studio y conectarse mediante autenticación de Windows con una cuenta que posea permisos administrativos.

Ejecutar el siguiente archivo:

```text
Database/SQLServer/01_IndigoLoftDB.sql
```

Este script crea la base de datos, las tablas y sus restricciones.

Está destinado a una instalación nueva.

### 5. Configurar el usuario JDBC

Desde SQL Server Management Studio:

1. Abrir la carpeta Seguridad del servidor.
2. Seleccionar Inicios de sesión.
3. Crear un nuevo inicio de sesión.
4. Establecer el nombre `indigo_app`.
5. Seleccionar autenticación de SQL Server.
6. Establecer una contraseña segura.

Posteriormente, ejecutar:

```text
Database/SQLServer/02_Configuracion_Usuario.sql
```

El usuario debe disponer de los permisos necesarios de lectura y escritura sobre IndigoLoftDB.

No es necesario otorgarle el rol de administrador del servidor.

### 6. Configurar las variables de entorno

El sistema utiliza variables de entorno para evitar almacenar credenciales directamente en el código fuente.

Crear las siguientes variables de usuario en Windows:

| Variable | Descripción |
|---|---|
| INDIGO_DB_PASSWORD | Contraseña del usuario SQL indigo_app |
| INDIGO_LOGIN_USER | Usuario de acceso a la aplicación |
| INDIGO_LOGIN_PASSWORD | Contraseña de acceso a la aplicación |

Para configurarlas:

1. Abrir la búsqueda de Windows.
2. Buscar Editar las variables de entorno del sistema.
3. Seleccionar Variables de entorno.
4. Crear las tres variables de usuario.
5. Guardar los cambios.
6. Reiniciar Apache NetBeans.

Las credenciales reales no deben publicarse en GitHub.

### 7. Configuración de la conexión JDBC

El sistema utiliza la siguiente configuración para desarrollo local:

```text
Servidor: localhost
Puerto: 1433
Base de datos: IndigoLoftDB
Usuario: indigo_app
```

URL de conexión:

```text
jdbc:sqlserver://localhost:1433;databaseName=IndigoLoftDB;encrypt=true;trustServerCertificate=true;
```

La contraseña se obtiene mediante la variable de entorno correspondiente.

La opción `trustServerCertificate=true` se utiliza para el entorno local de desarrollo y no debe considerarse una configuración recomendada para producción.

### 8. Abrir el proyecto en NetBeans

1. Abrir Apache NetBeans.
2. Seleccionar File → Open Project.
3. Seleccionar la carpeta del proyecto descargado.
4. Verificar que el proyecto utilice JDK 25.
5. Comprobar que la biblioteca JDBC esté correctamente reconocida.
6. Verificar la disponibilidad de la biblioteca AbsoluteLayout.
7. Resolver las referencias faltantes si NetBeans las solicita.

### 9. Compilar y ejecutar

En Apache NetBeans:

- Presionar F11 para compilar el proyecto.
- Presionar F6 para ejecutar la aplicación.

Clase principal:

```text
primer_avance_poo.PRIMER_AVANCE_POO
```

Una vez iniciado el sistema, utilizar las credenciales configuradas en las variables de entorno para acceder al menú principal.

## 💾 Copia de seguridad

El proyecto cuenta con una copia de seguridad completa de la base de datos sin registros de prueba.

```text
IndigoLoftDB_Final_V2.bak
```

El respaldo se distribuye de manera independiente al repositorio.

La restauración del backup constituye una alternativa a la instalación mediante el script de creación. No es necesario utilizar ambos métodos.

Después de restaurar el respaldo en otro servidor, puede ser necesario configurar nuevamente el inicio de sesión JDBC y asociarlo con el usuario de la base de datos.

El respaldo fue generado en SQL Server 2025.

## 🔐 Seguridad e integridad

- Credenciales administradas mediante variables de entorno.
- Usuario de base de datos con permisos limitados.
- Validación de documentos de identidad.
- Restricciones de integridad referencial.
- Validación de fechas y estados de reservas.
- Control de disponibilidad de habitaciones.
- Uso de transacciones en operaciones críticas.
- Exclusión de archivos privados mediante .gitignore.

## 🧪 Pruebas funcionales

Durante el desarrollo se realizaron pruebas locales relacionadas con:

- Registro, modificación y eliminación de entidades.
- Validación de campos.
- Conexión con SQL Server.
- Disponibilidad de habitaciones.
- Superposición de reservas.
- Check-in y Check-out.
- Cancelación de reservas.
- Facturación.
- Persistencia de información.
- Verificación de restricciones de base de datos.
- Verificación del respaldo SQL Server.

La instalación completa en un segundo equipo queda pendiente de validación.

## 👥 Equipo de desarrollo

| Integrante | Rol | Responsabilidades |
|---|---|---|
| Angel Eloy Marin Diaz | Líder del proyecto | Liderazgo técnico, conexión JDBC e interfaces gráficas |
| Valeria Sofia Hinostroza Alcalde | Sublíder del proyecto | Lógica de negocio y arquitectura del software |
| Juan Carlos Bautista Aguilar | Integrante | Equipo de desarrollo |
| Gian Carlos Sanchez Portal | Integrante | Equipo de desarrollo |
| Luis Lennon Vasquez Alayo | Responsable de documentación | Informe técnico, diagramas UML, modelado y documentación del proyecto |

## 🎓 Información académica

| Información | Detalle |
|---|---|
| Universidad | Universidad Privada del Norte |
| Facultad | Ingeniería |
| Carrera | Ingeniería de Sistemas Computacionales |
| Curso | Técnicas de Programación Orientada a Objetos |
| Ciclo | V ciclo |
| Periodo académico | 2026-II |
| Sede | Cajamarca, Perú |

## 📌 Estado del proyecto

Sistema desarrollado y sometido a pruebas funcionales locales.

Proyecto académico orientado a la aplicación práctica de la programación orientada a objetos, arquitectura de software, interfaces gráficas y bases de datos relacionales.

---

**INDIGO LOFT — Sistema de Gestión Hotelera**

Universidad Privada del Norte | Ingeniería de Sistemas Computacionales | 2026
