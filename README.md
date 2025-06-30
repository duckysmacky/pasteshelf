# Pasteshelf

Pasteshelf is a very simple and lightweight Pastebin-inspired application which allows for
its users to create, store and edit text (pastes)

## About

This is mostly just a _practice project_ / _project for portfolio_ to show off my Java and Spring
Boot skills and get some hands-on practice on working with REST APIs and Databases. Do mind that 
this is my first ever Java project which fully utilizes Spring Boot's ecosystem and features,
so not all the things are as perfect as they can be.

For now Pasteshelf serves just as a simple Pastebin clone, allowing to register accounts and create
different "pastes" associated with each user to store text. After creating an account, users can
create different pastes to store and share text. Each paste has its own **hash** by which it is later
identified, it is generated upon creation. Anyone can access and read a paste's data, but only 
authorized users can create new ones.


All the API documentation will be later added to project's wiki

## Installation and running

As of now this is not yet properly implemented.

You can manually build the project and run it as a Spring Boot application via Gradle:

```shell
gradle bootRun
```

## Features

Right now Pasteshelf's features are really basic and bare-bones:

- Custom user accounts
- Text storage in different pastes
- Paste creation and modification

Maybe in the future I will extend the functionality of this application to do other things as well
and add some other custom features.

## Technical specification

- Postgres SQL as the database
- HTTP Basic authorization
- Database migrations via Flyway
- API endpoint integration tests
- Custom exceptions and a global exception handler