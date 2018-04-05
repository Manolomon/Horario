# Eliminar Experiencia Eduactiva

### ID y Nombre:
CU03 - Eliminar EE

### Actor
Usuario

### Descripcion:
En este caso de uso, el Usuario selecciona una EE para eliminarla de la Base de Datos

### Flujo Normal:
1. El Sistema muestra el mensaje "¿Desea eliminar esta Experiencia Edactiva?"
2. El Usuario da click en Aceptar (ver FA - 3.1)
3. El Sistema elimina la EE de la Base de Datos (ver EX - 3.1)
4. El Sistema elimina las CLASE pertenecientes a la EE (ver EX - 3.1)
5. Fin del CU 

### Flujos Alternos
**FA - 3.1: Da click en Cancelar**
1. Fin del CU

### Excepciones
**EX - 3.1: Error con la conexión a la Base de Datos**
1. El sistema imprime el mensaje en el log con la Excepción
2. Fin