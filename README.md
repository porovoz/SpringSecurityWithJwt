### (RU)
# Spring Security with JWT

## Описание проекта:
Базовое веб-приложение с использованием Spring Security и JWT для аутентификации и авторизации пользователей.

Пользователи сохраняются в базу данных. Добавлена поддержка ролей пользователей (`USER` и `ADMIN`).
Пользователь с ролью `USER` имеет право доступа к ресурсам только к своим ресурсам.
Пользователь с ролью `ADMIN` имеет право доступа к как к своим ресурсам, так и к ресурсам пользователя с ролью `USER`.

#### REST API
URL: http://localhost:8080

- POST /auth/register - регистрация нового пользователя.
- POST /auth/login - авторизация пользователя.
- POST /auth/token - получение нового access токена, когда он стал недействителен.
- POST /auth/refresh - получение новых access и refresh токенов, когда они стали недействительны.
- GET /resources/user - получение доступа к ресурсам пользователя с ролью `USER`.
- GET /resources/admin - получение доступа к ресурсам пользователя с ролью `ADMIN`.

Подробнее: http://localhost:8080/swagger-ui/index.html

#### Регистрация нового пользователя
Для регистрации нового пользователя, сформируйте JSON с данными пользователя согласно образцу:

```json
{
  "username": "user",
  "email": "user@gmail.com",
  "password": "password",
  "role": "USER"
}
```

#### Авторизация пользователя
Для авторизации пользователя, сформируйте JSON с данными пользователя согласно образцу:

```json
{
  "email": "user@gmail.com",
  "password": "password"
}
```

После этого Вы получите действительные access и refresh токены для доступа к защищенным ресурсам.

#### Получение нового access токена, когда он стал недействителен.
Для получения нового access токена, когда он стал недействителен, сформируйте JSON с refresh токеном, 
полученным в процессе авторизации согласно образцу:

```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTcwNjQ2Njh9.Dm6bdJo2HzrSKjkjCbVcjdPrL5nEz29IACEtmVt_XcbiZJ65pjyeNzPZqitNKOQJeg-UyvbDDR4eCa09-jfCjA"
}
```

После этого Вы получите новый access токен для доступа к защищенным ресурсам, а refresh токен останется прежним.

#### Получение новых access и refresh токенов, когда они стали недействительны.
Для получения новых access и refresh токенов, когда они стали недействительны, сформируйте JSON с refresh токеном,
полученным в процессе авторизации согласно образцу:

```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTcwNjQ2Njh9.Dm6bdJo2HzrSKjkjCbVcjdPrL5nEz29IACEtmVt_XcbiZJ65pjyeNzPZqitNKOQJeg-UyvbDDR4eCa09-jfCjA"
}
```

Выберите тип авторизации - Bearer Token, и в поле авторизации введите действующий access токен согласно образцу:

```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTQ0NzYzMTMsInJvbGUiOiJBRE1JTiIsInVzZXJuYW1lIjoiY2hyaXN0b3BoZXIifQ.ixqKNx56FnDECXsRzECS1yagElUPZbU0wrn_135HATmuqbMJw4-RBEHS7iTJQoU0hHF0oIdCzXBtWPzqydnHHw
```

После этого Вы получите новые access и refresh токены для доступа к защищенным ресурсам.

#### Получение доступа к ресурсам пользователя с ролью `USER`
Для получения доступа к ресурсам пользователя с ролью `USER`, выберите тип авторизации - Bearer Token, 
и в поле авторизации введите действующий access токен согласно образцу:

```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTQ0NzYzMTMsInJvbGUiOiJBRE1JTiIsInVzZXJuYW1lIjoiY2hyaXN0b3BoZXIifQ.ixqKNx56FnDECXsRzECS1yagElUPZbU0wrn_135HATmuqbMJw4-RBEHS7iTJQoU0hHF0oIdCzXBtWPzqydnHHw
```

После этого Вам будет предоставлен доступ к защищенным ресурсам пользователя с ролью `USER`.

#### Получение доступа к ресурсам пользователя с ролью `ADMIN`
Для получения доступа к ресурсам пользователя с ролью `ADMIN`, выберите тип авторизации - Bearer Token, 
и в поле авторизации введите действующий access токен согласно образцу:

```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTQ0NzYzMTMsInJvbGUiOiJBRE1JTiIsInVzZXJuYW1lIjoiY2hyaXN0b3BoZXIifQ.ixqKNx56FnDECXsRzECS1yagElUPZbU0wrn_135HATmuqbMJw4-RBEHS7iTJQoU0hHF0oIdCzXBtWPzqydnHHw
```

После этого Вам будет предоставлен доступ к защищенным ресурсам пользователя с ролью `ADMIN`.

## Запуск приложения:
- клонировать проект в среду разработки;
- настроить подключение к базе данных в файле application.properties;
- запустить метод main в файле SpringSecurityWithJWTApplication.java

После этого будет доступен OpenAPI http://localhost:8080/swagger-ui/index.html.

## Технологии, используемые в проекте:
- Java 17;
- Spring Boot;
- Spring Data JPA;
- Spring Security;
- SpringDoc;
- Maven;
- REST API;
- Lombok;
- PostgreSQL;
- Liquibase.

### (EN)
# Spring Security with JWT

## Project description:
A basic web application using Spring Security and JWT for user authentication and authorization.

Users are saving to the database. Added support for user roles (`USER` and `ADMIN`).
Users with the `USER` role have the right to access only to their own resources.
Users with the `ADMIN` role have the right to access to their own resources 
and the resources of a users with the `USER` role.

#### REST API
URL: http://localhost:8080

- POST /auth/register - new user registration.
- POST /auth/login - user authorization.
- POST /auth/token - getting a new access token when it has become invalid.
- POST /auth/refresh - getting new access and refresh tokens when they become invalid.
- GET /resources/user - getting access to the resources of a user with the `USER` role.
- GET /resources/admin - getting access to the resources of a user with the `ADMIN` role.

More detailed: http://localhost:8080/swagger-ui/index.html

#### Registration of a new user
To register a new user, generate JSON with user data according to the sample:

```json
{
  "username": "user",
  "email": "user@gmail.com",
  "password": "password",
  "role": "USER"
}
```

#### User authorization
To authorize the user, generate JSON with the user's data according to the sample:

```json
{
  "email": "user@gmail.com",
  "password": "password"
}
```

After that, you will receive valid access and refresh tokens to access protected resources.

#### Getting a new access token when it has become invalid.
To get a new access token when it has become invalid, generate a JSON with a refresh token
obtained during the authorization process according to the sample:

```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTcwNjQ2Njh9.Dm6bdJo2HzrSKjkjCbVcjdPrL5nEz29IACEtmVt_XcbiZJ65pjyeNzPZqitNKOQJeg-UyvbDDR4eCa09-jfCjA"
}
```
After that, you will receive a new access token to access protected resources, 
and the refresh token will remain the same.

#### Getting new access and refresh tokens when they become invalid.
To get new access and refresh tokens when they become invalid, generate a JSON with the refresh token
obtained during the authorization process according to the sample:

```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTcwNjQ2Njh9.Dm6bdJo2HzrSKjkjCbVcjdPrL5nEz29IACEtmVt_XcbiZJ65pjyeNzPZqitNKOQJeg-UyvbDDR4eCa09-jfCjA"
}
```

Select the authorization type - Bearer Token, and in the authorization field enter a valid access token 
according to the sample:

```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTQ0NzYzMTMsInJvbGUiOiJBRE1JTiIsInVzZXJuYW1lIjoiY2hyaXN0b3BoZXIifQ.ixqKNx56FnDECXsRzECS1yagElUPZbU0wrn_135HATmuqbMJw4-RBEHS7iTJQoU0hHF0oIdCzXBtWPzqydnHHw
```

After that, you will receive new access and refresh tokens to access protected resources.

#### Getting access to the resources of a user with the `USER` role
To access the resources of a user with the `USER` role, select the authorization type - Bearer Token,
and enter a valid access token in the authorization field according to the sample:

```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTQ0NzYzMTMsInJvbGUiOiJBRE1JTiIsInVzZXJuYW1lIjoiY2hyaXN0b3BoZXIifQ.ixqKNx56FnDECXsRzECS1yagElUPZbU0wrn_135HATmuqbMJw4-RBEHS7iTJQoU0hHF0oIdCzXBtWPzqydnHHw
```

After that, you will be granted access to the protected resources of the user with the `USER` role.

#### Getting access to the resources of a user with the `ADMIN` role
To access the resources of a user with the `ADMIN` role, select the authorization type - Bearer Token,
and enter a valid access token in the authorization field according to the sample:

```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHJpc3RvcGhlckBnbWFpbC5jb20iLCJleHAiOjE3MTQ0NzYzMTMsInJvbGUiOiJBRE1JTiIsInVzZXJuYW1lIjoiY2hyaXN0b3BoZXIifQ.ixqKNx56FnDECXsRzECS1yagElUPZbU0wrn_135HATmuqbMJw4-RBEHS7iTJQoU0hHF0oIdCzXBtWPzqydnHHw
```

After that, you will be granted access to the protected resources of the user with the `ADMIN` role.

## How to run an application:
- clone the project into the development environment;
- configure the connection to the database in the application.properties file;
- run the main method in the SpringSecurityWithJWTApplication.java file.

After doing that OpenAPI by this URL http://localhost:8080/swagger-ui/index.html.

## Technologies:
- Java 17;
- Spring Boot;
- Spring Data JPA;
- Spring Security;
- SpringDoc;
- Maven;
- REST API;
- Lombok;
- PostgreSQL;
- Liquibase.
- Liquibase.