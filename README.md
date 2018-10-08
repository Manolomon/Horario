[![GitHub last commit](https://img.shields.io/github/last-commit/Manolomon/Horario.svg?style=for-the-badge)](https://github.com/Manolomon/Horario)
# Horario

Proyecto Horario, orientado a un estudiante con un modelo educativo similar al que se observa en la Facultad. Se debe poder:

* [x] Agregar distintas Experiencias Educativas, las cuales se impartiran una o más veces por semana, en distintos horarios y salones
* [x] Obtener una vista general del horario
* [X] Gestionar las clases individuales de cada Experiencia Educativa
* [X] Almacenar la información en una base de datos SQL

## Uso

1. Instalar MySQL con un usuario root registrado con la contraseña: **1234**
2. Ejecutar el script ubicado en /database/Horario.sql
    ```
    SOURCE C:\Ruta_absoluta\database\Horario.sql;
    ```
3. Ejecutar ```Timetable.jar```, ubicado como release
4. Disfrutar c:

## Implementación

El proyecto está almacenado como proyecto de Netbeans, así que se puede inicializar desde ```File > Open Project > carpeta_del proyecto```

## Objetivo

Que el estudiante aplique los conocimientos adquiridos en temas previos para la escritura de código guiado por estándares y buenas prácticas.

## Descripción

Escribir un programa en java para el manejo de horarios de clase de los estudiantes, dicha aplicación permitirá lo siguiente:

* Agregar una experiencia educativa al horario de clases donde se especifique el día de la clase, horario, salón  y profesor asignado.
* Eliminar la experiencia educativa del horario de clases.

### Los puntos a calificar son los siguientes:

* Definición y aplicación de un estándar de codificación, debe mencionar el estilo de indentación que está utilizando.
* Diseño preliminar de la aplicación orientado a objetos.
* Definición de las partes o subsistemas que considerará en el desarrollo de la aplicación.
* Aplicación de principios de orientación a objetos: ocultamiento de información, herencia.
* Complejidad de métodos menor a 10.
* Comentarios en código
* Documentación final

### Preguntas de apoyo

* ¿El código implementa correctamente el diseño planteado?
* ¿El código está apegado a un estándar de codificación?
* ¿El código está bien estructurado, es consistente en estilo y en formato?
* ¿Existe algún código que no es utilizado?
* ¿Existe código que puede ser agrupado en componentes reutilizables o librerías?
* ¿Existen métodos complejos que pueden reestructurarse en otros?
* ¿Los comentarios del código son claros y utilizan un estilo fácil de mantener?
* ¿Las variables describen el contenido de las mismas y tienen nombres claros?
* ¿Hay variables redundantes o sin uso?

## Prototipo

![Prototipo](/doc/Horario.png)

## Version Final

### Vista General

![Version Final](/doc/Horario_Finale.png)

### Agregar Clases

![Prototipo](/doc/HorarioAgregarClase_Finale.png)

### Vista General de Experiencias Educativas

![Prototipo](/doc/HorarioEE_Finale.png)

## Diagrama de Casos de Uso

![Casos de Uso](/doc/CasosUso.png)

## Modelo de Dominio

![Modelo de Dominio](/doc/ModeloDominio.png)

## Base de Datos

Archivo reservado para la carga en MySQL en /database/Horario.sql

### EE

| Field    | Type         | Null | Key | Default | Extra          |
| -------- | ------------ | ---- | --- | ------- | -------------- |
| idEE     | int(11)      | NO   | PRI | NULL    | auto_increment |
| nombre   | varchar(255) | NO   |     | NULL    |                |
| profesor | varchar(255) | NO   |     | NULL    |                |

### Clase

| Field      | Type        | Null | Key | Default | Extra          |
| ---------- | ----------- | ---- | --- | ------- | -------------- |
| idClase    | int(11)     | NO   | PRI | NULL    | auto_increment |
| idEE       | int(11)     | NO   | PRI | NULL    |                |
| dia        | varchar(15) | NO   |     | NULL    |                |
| horaInicio | time        | NO   |     | NULL    |                |
| horaFin    | time        | NO   |     | NULL    |                |
| salon      | varchar(50) | NO   |     | NULL    |                |
| nota       | text        | YES  |     | NULL    |                |

## Third-Party Software

### MySQL Connector/J

    https://github.com/mysql/mysql-connector-j#mysql-connectorj

### JFoenix

    https://github.com/jfoenixadmin/JFoenix

### MyBatis SQL Mapper Framework for Java

    https://github.com/mybatis/mybatis-3

### JFXtras

    https://github.com/JFXtras/jfxtras
