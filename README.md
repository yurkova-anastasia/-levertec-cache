# ClevertecTask

## Description

Основные сущности:

- User

## Configuration

[Configuration](src/main/resources/application.yml)

### Build tool

This project uses such build tool as gradle.<br/>
Use "./gradle build" to build project.


### Manual

To start the project you need:

- Have postgresql, pgAdmin
- Create empty database in pgAdmin
- in application.yml set the necessary data to connect to the database:
  url, username, password.
- data in the database is filled in automatically when starting the project

## REST-services:

### GET http://localhost:8081/users?id={id}

##### getting bank on id

        where:
        {id} - user id

### PUT http://localhost:8081/users/

##### update user

         Example of request body:
         {
            "name": "John",
            "surname": "Doe",
            "age": 25,
            "birthdate": "1998-01-01"
         }

### POST http://localhost:8081/users?id={id}

##### create user

         Example of request body:
         {
            "id": 5,
            "name": "John",
            "surname": "Doe",
            "age": 25,
            "birthdate": "1998-01-01"
         }

### DELETE http://localhost:8081/users?id={id}

##### delete user

         where:
         {id} - user id

### GET http://localhost:8081/check/user?id={id}

##### print user info to PDF

         where:
         {id} - user id