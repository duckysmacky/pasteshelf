# Pasteshelf

Pasteshelf is a very simple and lightweight Pastebin-inspired application which allows for
its users to create, store and edit text (pastes)

## About

This is mostly just a _practice project_ / _project for portfolio_ to show off my Java and Spring
Boot skills and get some hands-on practice on working with REST APIs and databases. Do mind that 
this is my first ever Java project which fully utilizes Spring Boot's ecosystem and features,
so not all the things are as perfect as they can be.

For now Pasteshelf serves just as a simple Pastebin clone, allowing to register accounts and create
different "pastes" associated with each user to store text. After creating an account, users can
create different pastes to store and share text. Each paste has its own **hash-id** which is generated 
upon creation and used to identify a specific paste. Anyone can access and read a paste's data, but 
only authorized users can create new ones.

All the API documentation will be **later added to project's wiki**

## Installation and running

First you have to clone the project locally. Then, there are two ways to run Pasteshelf: local build or via Docker

The project requires a correctly set up `.env` file with database and port details. There is a `.env.example` file to
help you set up your own `.env` file.

### Local build

You can manually build the project and run it as a Spring Boot application via Gradle

```shell
# unix
./gradlew bootRun
# windows
.\gradlew.bat bootRun
```

Make sure that your Postgres server has a valid database and user with password for Pasteshelf to use which match those 
provided in the `.env` file.

### Via Docker

You can build a local Docker image and launch it using `docker-compose`. This also comes with an instance of a
preconfigured Postgres database

```shell
docker-compose up
```

## Features

Right now Pasteshelf's hash the following features:

- Custom user accounts
- Text storage in different pastes
- Paste creation and modification

For now, they are really basic and bare-bones, but in the future I will extend the functionality of this application to
do other things as well and add some custom features.

## Technical specification

- Postgres SQL as the database
- All CRUD paste operations implemented
- HTTP Basic authorization
- Database migrations via Flyway
- API endpoint integration tests
- Custom exceptions and a global exception handler
- Project Dockerazation (image + compose)
