# ARQUITECTURA DE APLICACIONES MASIVAMENTE DISTRIBUIDAS - TWITTER AYGO

## Descripción del Laboratorio

Este laboratorio consiste en diseñar e implementar una arquitectura de microservicios para replicar algunas funcionalidades básicas de Twitter. Para esto se ha basado el enfoque de servicios distribuidos.

## 1. Análisis del Contexto y Recursos

Para implementarla arquitectura solución orientada a microservicios, se definieron dos funcionalidades del twitter original:

* TweetService: CRUD de tweets.
* UserService: Creación, lectura y eliminación de los usuarios.

Cada microservicio usa un cliente dynamo y utilidades que facilitan el uso de anotaciones para el tratamiento de las peticiones y de los objetos json.

### Diagrama de Clases

![Diagrama de Clases](/img/diagramaClases.png)

En el diagrama de clases, se pueden identificar los principales objetos en el dominio del problema; Usuario y Tweet.

### 2. Métodos

Además de los objetos principales, se han identificado los métodos para respaldar las funcionalidades del sistema:

- **Métodos para Usuario:**
  - `getUserById()`
  - `getUsers()`
  - `createUser()`
  - `deleteUserById()`

- **Métodos para Tweet:**
  - `getTweetById()`
  - `getTweets()`
  - `createTweet()`
  - `deleteTweetById()`
  - `updateTweet()`

### 3. Representación de Recursos

Los recursos (users y tweets) se representan utilizando estructuras de datos JSON. Por ejemplo:

```json
// Representación de un Usuario
    {
        "userId": "Marisol181223",
        "userName": "Marisol",
        "userEmail": "marisol@gmail.com"
    }
// Representación de un Tweet
    {
        "tweetId": "Marisol1812232023-12-18",
        "userId": "Marisol181223",
        "tweetMessage": "Mensaje de prueba",
        "tweetCreated": "2023-12-18"
    }
```

Y los métodos mencionados anteriormente son respaldados por las siguientes rutas de la API:

- `/tweets`
  - `GET /` - Obtiene la información de todos los Tweets.
  - `POST /` - Crea un nuevo Tweet.
  - `/{idTweet}`
    - `GET /` - Obtiene la información de un Tweet.
    - `DELETE /` - Elimina un Tweet.
    - `UPDATE /` - Cambia datos de un Tweet.

- `/users`
  - `GET /` - Obtiene la información de todos los Usuarios.
  - `POST /` - Crea un nuevo Usuario.
  - `/{idUser}`
    - `GET /` - Obtiene la información de un Usuario.
    - `DELETE /` - Elimina un Usuario.

### 4. Arquitectura de la Solución

![Arquitectura](/img/diagramaArquitectura.png)

La arquitectura se compone de tres partes principales:

- **Api Gateway:** Punto de entrada que enruta las solicitudes a los servicios adecuados.
- **Funciones Lambda:** Lógica de la aplicación implementada como funciones independientes.
- **Dynamo DB:** Almacenamiento de datos en bases de datos NoSQL.

### 5. Implementación del Prototipo

El prototipo se ha implementado utilizando los servicios de AWS previamente mencionados y estos son los resultados:

#### Usuarios
* Creación Usuarios
![Creación usuario](/img/cUser1.jpeg)
![Creación usuario](/img/cUsser2.jpeg)
![Creación usuario](/img/cUser3.jpeg)
![Creación usuario](/img/cUser4.jpeg)
* Lectura Usuarios
![Lectura](/img/gUsers.jpeg)
![Lectura](/img/gUser.jpeg)
* Eliminación Usuario
![Eliminación](/img/dUser.jpeg)

#### Tweets
* Creación Tweets
![Creación tweet](/img/cTweet1.jpeg)
![Creación tweet](/img/cTweet2.jpeg)
![Creación tweet](/img/cTweet3.jpeg)
* Lectura Tweets
![Lectura](/img/gTweets.jpeg)
![Lectura](/img/gTweet.jpeg)
* Actualización Tweet
![Actualización](/img/uTweet.jpeg)
* Eliminación Tweet
![Eliminación](/img/dTweet.jpeg)
* Tweets Finales
![Lectura](/img/gTweets2.jpeg)