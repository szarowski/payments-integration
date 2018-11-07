# Payment Integration API

The Payment Integration API.

## Building and running the service

The project is build in Gradle, use:
```
gradle clean build
gradle bootRun
```

OR in case you want to run it with Gradle Wrapper:

```
./gradlew clean build
./gradlew bootRun
```

## Design overview

The Payment Integration API is build on Java 8+ and Spring Boot 2.0.6
and Finchley.SR2 version of Spring Boot Cloud for Feign clients used
for communication with external APIs (TranswerWise Quote API:
`https://api.sandbox.transferwise.tech/v1/quotes`).

It fetches the data from TranswerWise sandbox based on the input parameters.
One parameter is a path parameter to the temporary database table of users.
To obtain an auto-generated id of the user you need to call the `/v1/users` 
GET endpoint first. Take the id of one of the pre-initialised users and use 
as a path parameter to `/v1/payments/<id>` POST endpoint. Create a RequestBody 
for POST endpoint as e.g. `{"paymentAmount":12.23}`. You can see some examples below.
It will create a payment which you can display by calling
 `/v1/payments` GET endpoint.

The application uses the H2 in-memory database which is created by running the application:
`gradle bootRun`. Once you stop the application, database is destroyed.
Tests are split into Unit tests and Integration tests based on the suffix 
(ITest means integration test). The code coverage is 98 percent.

The SQL scripts are located in the resources under the `/db/migration` path
used by the Flyway migration tool.

The application consists of REST Controllers, Service handling the business logic
for the Payment Controller, Repositories for `users` and `payments` tables, Transformer
for transforming data and RowMappers for mapping the query data.
Interfaces are documented. 

## Endpoints
```
Create Payment:           POST   /v1/payments/<id>
Get Payments:             GET    /v1/payments
Get Users:                GET    /v1/users
```

### Examples

#### Get all users:
curl -X GET http://localhost:8080/v1/users

#### Get all payments:
curl -X GET http://localhost:8080/v1/payments

#### Create payment:
curl -X POST http://localhost:8080/v1/payments/`<id_from_get_users_endpoint>` -H "Content-Type: application/json" -d '{"paymentAmount":12.23}'

## Requirements

Installed Java 8+.
Installed Gradle. 
Compile with `-parameters` flag. Gradle build already does that.

## Database schema

This is a merge from Flyway migration. Note that the alter table at the end unifies timestamps with timezones.
```
CREATE TABLE IF NOT EXISTS payments (
    id             INTEGER          NOT NULL,
    source         VARCHAR(3),
    target         VARCHAR(3),
    source_amount  DECIMAL(20, 2),
    target_amount  DECIMAL(20, 2),
    rate           DECIMAL(20, 2),
    fee            DECIMAL(20, 2),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id              UUID            NOT NULL,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    payout_currency VARCHAR(3),

    PRIMARY KEY (id)
);
```