# Spring Framework 3.1.0 Playground

The objective of this project is to test some spring modules. I'm using the following tools:

- Java 21
- PostgreSQL 15
- Keycloak
- OpenAPI
- Docker
- Spring HATEOAS
- Spring Data JPA

## How to Run

_To run this project you must have Docker running on your machine._

To create the app image I use a Dockerfile with multi-stage building and you can change any
configuration if you want.

On your command line terminal just run:

`$ docker-compose up`

Remember to be on the project folder.


## Endpoints

| Method   | URL         | Description               |
|----------|-------------|---------------------------|
| `GET`    | `/car`      | List all cars.            |
| `GET`    | `/car/{id}` | Return the specified car. |
| `POST`   | `/car`      | Create a new car.         |
| `PUT`    | `/car/{id}` | Update existing car.      |
| `DELETE` | `/car/{id}` | Delete the specified car. |
| `POST`   | `/user`     | Create a new user.        |

## Database

I choose PostgreSQL because it's a robust and powerful DBMS and fits this application.

When running docker-compose it will create a folder named **data**  and will contain all files used by database.

## OAuth

TODO