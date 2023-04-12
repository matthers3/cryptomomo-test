# cryptomomo-test

Aplicación de visualización de criptomonedas utilizando Android Studio con el lenguaje JAVA, utilizando la estructura de MVVM, con Views y XML.

# Decisiones Importantes

- Java vs Kotlin: AL investigar de ambos lenguajes, entendí que Kotlin era un lenguaje más moderno que Java. Tomé la decisión de desarrollar en Java con la finalidad de familiarizarme con el lenguaje, ya que no lo habia utilizado con anterioridad y me pareció más complicado que Kotlin.

- MIN SDK: el SDK utilizado corresponde a la API24 (Android 7.0). Era la SDK recomendad por Android Studio y podía correr en aproximadamente el 94,4% de los dispositivos según este mismo programa. Me pareció un buen punto medio entre una SDK antigua y una más reciente.

- APIKey: Almacené el valor de la APIkey como un secreto en la configuración de Build de Gradle. Para esto fue necesario crear un archivo ```apikey.properties``` en el root del proyecto y llamar dicho archivo en ```gradle.build```, el archivo de propiedades sigue la siguiente estructura:
     ```
     API_KEY=XXXXX-XXXXX-XXXXX-XXXXX-XXXXX
     ```

- Uso de Repository: Se implementó una base de datos local, para almacenar el precio de Bitcoin que desencadena una notificación. También posee un bool para identificar si una notificación ya fue enviada, con la finalidad de no enviar múltiples notificaciones, dicha funcionalidad quedó pendiente.

# Mejoras

Esta es la primera aplicación nativa que he desarrollado, por lo que espero de antemano varios errores y malas prácticas.

- Uso de JSONObject: Por algún motivo realizar ```Object.get("string")``` de algunos valores responde con un warning de ```NullPointerException```, aunque dicho error no ocurre. Me gustaría investigar más el cómo satisfacer dichos warnings al usar JSONs anidados.

- Notificaciones: Las notificaciones se sobreescriben, me gustaría haber implementado que no active nuevas notificaciones hasta abrir la app o se descarte la última notificación. 

- Botones: Me hubiese gustado evitar los botones, que el dato de precio de Bitcoin se almacenara mintras se escribía y que la tabla se actualizara haciendo scroll hacia arriba. Utilizar botones me fue lo más sencillo, es la primera vez que hacía vistas con XML para una aplicación nativa.

- Imagenes: Me hubiese gustado colocar imagenes de las criptomonedas, pense en usar web scrapping, pero no me dio el tiempo.

# Bugs

Bugs que podría identificar podrían ser:

- Botón guardar valor: Apretar el botón de guardar valor antes de hacer desaparecer el teclado deja el teclado abierto pero sin focus. No es precisamente un bug, pero podría ser confuso o molesto.

- Sin internet: No puse un placerholder que aparezca mientras no hay valores en la tabla, ni tampoco un indicador de actividad que muestre que está cargando, se puede ver en blanco e interpretar como un bug, pero es un problema de conexión.

# Nuevas Features


- Se podrían incluri más criptomonedas a las notificaciones, quedaría rápido modificando un modelo de datos que tengo.
