# Taller_Modelado_Validacion
Integrantes<br>
<b>LUZ YENNY CARDENAS HIDALGO</b><br>
<b>HAROLD PUENTES ALFONSO</b><br>
<b>NESTOR LEONEL OSORIO TOVAR</b><br>


Diseño: Arquitectura orientada a servicios, creamos un servicio orquestador que se encarga de manejar el proceso, y de consumir los 
microservicios creados, se identifica claramente como apoya un buen modelo de arquitectura empresarial, es independiente de los 
proveedores, se consumen servicios independientes de la plataforma, los servicios que construimos están conducidos por el negocio que 
en este caso es proveer la opción de pagos de servicios con diferentes convenios, y están centrados en la composición, creamos diferentes 
servicios con una tarea específica para cada uno (agnósticos).

Manejamos diseño estandarizado de contratos, creándolos en swagger, y definiendo los esquemas aparte del contrato. 
Primero se definieron los contratos y los esquemas y a partir de estos se generaron los servicios, con esto logramos desacoplamiento 
del contrato y del servicio como tal patrón Decoupled Contract, Manejamos bajo acoplamiento ya que cada servicio cumple con una tarea del
proceso específica. En la definición de los contratos tratamos de manejar el concepto de Diseño de Servicio Abstracto teniendo en cuenta 
que solo contienen información básica del servicio. Consideramos que los servicios que creamos son reusables, teniendo en cuenta que 
manejamos los datos de los servicios que queremos consumir en archivos externos en donde definimos los endpoint y las características de 
los mismos.

Se implemento un inventario de servicios para publicar y registrar los servicios  en WSO2, 
domain inventory para el gobierno de los servicios 
Otro patrón que manejamos fue el de Schema Centralization teniendo en cuenta que creamos esquemas aparte de los contratos.
<br>
<b>FUNCIONALIDAD</b>
<br>
Este proyecto funciona con  3 servicios independientes:

ORQUESTADOR, recibe una peticion del cliente conformada por un numero de factura, una operacion a realizar (consultar, pagar, compensar)
y realiza una peticion al siguiente servicio
ROUTING,  con los datos recibidos del servicio orquestador lee de un archivo de texto a que convenio pertenece la factura, teniendo en 
cuenta los primeros 4 digitos de la factura, retornando la url del servicio a consumir junto con tipo de servicio (rest, soap), tipo 
operacion (consultar, pagar, compensar), metodo (GET, POST, DELETE). Esta informacion se retorna al orquestador.
TRANSFORMADOR Y ENRUTAMIENTO, Recibe una peticion del orquestador, con los datos necesarios para el consumo del servicio, en este servicio
se realiza una peticion al servicio que no conocemos y que no es de nuestro gobierno a traves de transformacion XSLT. Recibe una respuesta
del externo en formato XML, la cual se convierte a JSON y retorna el resultado de la operacion al orquestador.

Los servicios que se conocen y son externos se encuentran aqui:

https://github.com/germansua/UJaveriana-AES-ModVal/tree/master/Workshop1


Gracias.
