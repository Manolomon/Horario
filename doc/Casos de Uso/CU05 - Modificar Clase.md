# Modificar Clase

### ID y Nombre:

CU05 - Modificar Clase

## Actor

Usuario

## Descripcion:

En este caso de uso, el Usuario gestiona los datos dentro de una Clase almacenada en la Base de Datos

## Flujo Normal:

1. El Sistema muestra los datos de la CLASE seleccionada (dia, horaInicio, horaFin, salon)(ver EX - 5.1)
2. El Usuario modifica los datos deseados y da click en Aceptar (ver FA - 5.1)
3. El Sistema valida los datos ingresados (ver FA - 5.2)
4. El Sistema almacena los cambios de la CLASE en la Base de Datos
5. Fin del CU

## Flujos Alternos

### FA - 5.1: Da click en Cancelar

1. Fin del CU

### FA - 5.2: Campos Incompletos

1. El Sistema muestra un mensaje "Ingrese todos los datos solicitados"
2. Regresa al punto 2 del Flujo Normal

## Excepciones

### EX - 5.1: Error con la conexión a la Base de Datos

1. El sistema imprime el mensaje en el log con la Excepción
2. Fin