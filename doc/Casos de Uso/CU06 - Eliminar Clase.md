# Eliminar Clase

### ID y Nombre:
CU06 - Eliminar Clase

### Actor
Usuario

### Descripcion:
En este caso de uso, el Usuario selecciona una Clase para eliminarla de la Base de Datos

### Flujo Normal:
1. El Sistema muestra el mensaje "¿Desea eliminar esta Clase?"
2. El Usuario da click en Aceptar (ver FA - 6.1)
3. El Sistema elimina la CLASE de la Base de Datos (ver EX - 6.1)

### Flujos Alternos
**FA - 6.1: Da click en Cancelar**
1. Fin del CU

### Excepciones
**EX - 6.1: Error con la conexión a la Base de Datos**
1. El sistema imprime el mensaje en el log con la Excepción
2. Fin