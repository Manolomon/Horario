# Modificar Experiencia Eduactiva

### ID y Nombre:
CU02 - Modificar EE

### Actor
Usuario

### Descripcion:
En este caso de uso, el Usuario gestiona los datos dentro de una EE almacenada en la Base de Datos

### Flujo Normal:
1. El Sistema muestra los datos de la EE seleccionada (nombre, profesor)(ver EX - 2.1)
2. El Usuario modifica los datos deseados y da click en Aceptar (ver FA - 2.1) (ver FA - 2.2)
3. El Sistema valida los datos ingresados (ver FA - 2.3)
4. El Sistema almacena los cambios de la EE en la Base de Datos
5. Fin del CU

### Flujos Alternos
**FA - 2.1: Da click en Cancelar**
1. Fin del CU

**FA - 2.2: Da click en Agregar Clase**
1. Su el Usuario da click en Agregar Clase [Extiende al CU04 - Agregar Clase]
2. Regresa al punto 2 del Flujo Normal

**FA - 2.3: Campos Incompletos**
1. El Sistema muestra un mensaje "Ingrese todos los datos solicitados"
2. Regresa al punto 2 del Flujo Normal

### Excepciones
**EX - 2.1: Error con la conexión a la Base de Datos**
1. El sistema imprime el mensaje en el log con la Excepción
2. Fin