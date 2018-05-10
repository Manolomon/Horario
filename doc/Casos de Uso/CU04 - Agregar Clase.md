# Agregar Clase

### ID y Nombre:
CU04 - Agregar Clase

### Actor
Usuario

### Descripcion:
En este caso de uso, el Usuario agrega una Clase a alguna EE existente

### Flujo Normal:
1. El Sistema solicita los datos de la CLASE (dia, horaInicio, horaFin, salon)
2. El Usuario ingresa los datos de la CLASE y da click en Aceptar (ver FA - 4.1)
3. El Sistema validad los datos completos (ver FA - 4.2)
4. El Sistema almacena la CLASE en la Base de Datos (ver EX - 4.1)
5. Fin del CU

### Flujos Alternos
**FA - 4.1: Da click en Cancelar**
1. Fin del CU

**FA - 4.2: Campos Incompletos**
1. El Sistema muestra un mensaje "Ingrese todos los datos solicitados"
2. Regresa al punto 2 del Flujo Normal

### Excepciones
**EX - 4.1: Error con la conexión a la Base de Datos**
1. El sistema imprime el mensaje en el log con la Excepción
2. Fin