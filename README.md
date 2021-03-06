# Parcial1-Arep-OpenWeather
Parcial 1 de arep

# PARCIAL 1 AREP VERIFICACIÓN DE CONOCIMIENTOS TÉCNICOS
La aplicación recibe en un campo la descripción de una ciudad, por ejemplo “London” para Londres y muestra la información del clima para esa ciudad. Para esto utiliza el API gratuito de https://openweathermap.org/

## Autor
* Miguel Angel Fuquene ARias

## Contrucción
* NetBeans - Editor de código y ambiente de desarrollo
* Maven - Gestor de dependencias

# Lenguajes
* JavaScript
* Java
* Html

## Pre-Requisitos
* Tener git instalado
* Tener maven instalado
* Usar la version de Java 8

## Utilización
Para probar la siguiente Api nos tenemos que dirigir al siguiente link:
[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://arep-parcial1-openweather.herokuapp.com/clima)

## Requerimientos

Su compañía lo ha seleccionado para construir una aplicación web simple desplegada en Heroku para uno de los clientes más importantes.
La aplicación debe consultar el estado del clima en lugares específicos de la tierra.  La aplicación recibirá en un campo la descripción de una ciudad, por ejemplo “London” para Londres   y deberá mostrar la información del clima para esa ciudad. Para esto utilice el API gratuito de https://openweathermap.org/ (Puede crear una cuenta para obtener la llave para realizar consultas). La petición debe pasar por su servicio web desplegado en Heroku, es decir desde su servicio en Heroku se debe invocar el servicio web de clima. El usuario no sabrá qué servicio está usted invocando por detrás. Utilice el servicio "Current Weather Data" de openweathermap.org.

Debe usar sockets solamente no puede usar ni spark ni spring. Usted debe implementar al menos dos servicios web, uno que retorne la página (La página debe estár quemada en el código) y otro que retorne le Json con los datos del clima.

La página debe invocar el servicio web de la ciudad de manera asíncrona y modificar la información directamente.

El API de la página debe ser el siguiente:
{url del servicio en heroku}/clima

El API de su servicio debe ser el siguiente:
{url del servicio en heroku}/consulta?lugar={ciudad o lugar}

El servicio debe reornar un Json exactamente igual al que retorna el servicio de openweathermap denominado "Current Weather Data". Asegurese que el tipo de retorno sea Json.

Sugerencia realice la implementación de manera incremental. Haga commits regulares.
