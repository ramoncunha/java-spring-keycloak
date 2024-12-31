# Spring Framework 3.1.0 Playground

The objective of this project is to test some spring modules. I'm using the following tools:

- Java 21
- Spring Data JPA
- PostgreSQL 15
- Spring Security
- Keycloak
- Docker
- Spring HATEOAS

## How to Run

_To run this project you must have Docker running on your machine._

To create the app image I use a Dockerfile with multi-stage building and you can change any
configuration if you want.

On your command line terminal just run:

`$ docker compose up`

Spring API uses port 8080, PostgreSQL 5432 and Keycloak 8443. 

If you want to run on your local machine use this command:

`$ docker compose -f docker-compose-local.yaml up`

Remember to be on the project folder.

### Keycloak
You must configure Keycloak to use this application. Follow these steps:

1. Go to `http://localhost:8443/`
2. Create a new realm named `car-realm`
3. Create a new client to connect with this API:
    - Client Id: `carapi`
    - Client authentication: ENABLE - this will make our access to keycloak private
    - Direct access grants: ENABLE
4. Create new realm roles: `CARAPI_CREATE`, `CARAPI_UPDATE`, `CARAPI_DELETE`
5. Create a new group and assign these roles to it
6. Create a new user and assign the user to this group

### How to generate a token

Since client authentication is enabled, you need to retrieve the client secret. Navigate to Client > carapi > Credentials
in Keycloak console.
After obtaining the secret, use it in the following request:

```curl
curl --location 'http://localhost:8443/realms/car-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=carapi' \
--data-urlencode 'client_secret=GENERATED_SECRET' \
--data-urlencode 'username=YOUR_USER' \
--data-urlencode 'password=YOUR_USER'
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

## OAuth

Open Keycloak Admin Console using http://localhost:8443