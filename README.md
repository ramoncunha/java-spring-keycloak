# Spring Playground Sample Application

The objective of this project is to test some spring modules. I'm using the following tools:

- Java 21
- Spring Data JPA
- PostgreSQL 15
- Spring Security
- Keycloak
- Docker
- Spring HATEOAS
- GitHub Actions
- Spring Boot's layer tools for Docker

## How to Run

_To run this project you must have Docker running on your machine.
Remember to be on the project folder._

To create the app image I use a Dockerfile with multi-stage building and you can change any
configuration if you want.

Let's get this app running! On your command line terminal just run:

```
docker compose up --build
```

This command will run this API on port 8080, PostgreSQL on port 5432 and Keycloak on port 8181.
You can go to Keycloak section and see how to configure authentication.

### Run locally

If you want to run this application on your IDE we will run only PostgreSQL and Keycloak on Docker.
Use the following command:

```
docker compose -f docker-compose-local.yaml up
```

Open your favorite IDE and set the following VM Option before run this app:

```
-Dspring.profiles.active=local
```

We do rhis do get the correct environment variables. Now you can run and code!

## Keycloak configuration - Automatic

Execute the `init-keycloak.sh` script to create users, clients, groups, and roles.
This script will:
- Create the car-realm
- Configure the car-api client
- Set up roles and groups
- Return the admin token

Note: Ensure the script has execution permissions before running it.

## Keycloak configuration - Manual mode
You must configure Keycloak to use this application. Follow these steps:

1. Go to `http://localhost:8181/`
2. Create a new realm named `car-realm`
3. Create a new client to connect with this API:
    - Client Id: `car-api`
    - Client authentication: ENABLE - this will make our access to keycloak private
    - Direct access grants: ENABLE
4. Create new realm roles: `CARAPI_CREATE`, `CARAPI_UPDATE`, `CARAPI_DELETE`
5. Create a new group and assign these roles to it
6. Create a new user and assign the user to this group

### How to generate a token

Since client authentication is enabled, you need to retrieve the client secret. Navigate to Client > car-api > Credentials
in Keycloak console.
After obtaining the secret, use it in the following request:

```curl
curl --location 'http://localhost:8181/realms/car-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=car-api' \
--data-urlencode 'client_secret=GENERATED_SECRET' \
--data-urlencode 'username=YOUR_USER' \
--data-urlencode 'password=YOUR_PASSWORD'
```

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

When running docker-compose it will create a folder named **dbdata**  and will contain all files used by database.