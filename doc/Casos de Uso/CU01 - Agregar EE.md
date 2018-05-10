# Agregar Experiencia Eduactiva

### ID y Nombre:
CU01 - Agregar EE

### Actor
Usuario

### Descripcion:
En este caso de uso, el Usuario agrega una EE a la Base de Datos

### Flujo Normal:
1. El Sistema solicita los datos de la EE (nombre, profesor)
2. El Usuario ingresa los datos de la EE y da click en Aceptar (ver FA - 1.1)
3. El Sistema validad los datos completos (ver FA - 1.2)
4. El Sistema almacena la EE en la Base de Datos (ver EX - 1.1)
5. [Incluye CU06 - Agregar Clase]
6. Fin del CU

### Flujos Alternos
**FA - 1.1: Da click en Cancelar**
1. Fin del CU

**FA - 1.2: Campos Incompletos**
1. El Sistema muestra un mensaje "Ingrese todos los datos solicitados"
2. Regresa al punto 2 del Flujo Normal

### Excepciones
**EX - 1.1: Error con la conexión a la Base de Datos**
1. El sistema imprime el mensaje en el log con la Excepción
2. Fin# Agregar Experiencia Eduactiva

### ID y Nombre:
CU01 - Agregar EE

### Actor
Usuario

### Descripcion:
En este caso de uso, el Usuario agrega una EE a la Base de Datos

### Flujo Normal:
1. El Sistema solicita los datos de la EE (nombre, profesor)
2. El Usuario ingresa los datos de la EE y da click en Aceptar (ver FA - 1.1)
3. El Sistema validad los datos completos (ver FA - 1.2)
4. El Sistema almacena la EE en la Base de Datos (ver EX - 1.1)
5. [Incluye CU06 - Agregar Clase]
6. Fin del CU

### Flujos Alternos
**FA - 1.1: Da click en Cancelar**
1. Fin del CU

**FA - 1.2: Campos Incompletos**
1. El Sistema muestra un mensaje "Ingrese todos los datos solicitados"
2. Regresa al punto 2 del Flujo Normal

### Excepciones
**EX - 1.1: Error con la conexión a la Base de Datos**
1. El sistema imprime el mensaje en el log con la Excepción
2. Fin