#!/usr/bin/env bash

# Thanks to Ivan Franchin where I get this script
# https://github.com/ivangfr/springboot-react-keycloak

if [[ -z $(docker ps --filter "name=keycloak" -q) ]]; then
  echo "[WARNING] You must initialize the envionment (./init-environment.sh) before initializing keycloak"
  exit 1
fi

KEYCLOAK_HOST_PORT=${1:-"localhost:8181"}
echo
echo "KEYCLOAK_HOST_PORT: $KEYCLOAK_HOST_PORT"

echo
echo "Getting admin access token"
echo "--------------------------"

ADMIN_TOKEN=$(curl -s -X POST "http://$KEYCLOAK_HOST_PORT/realms/master/protocol/openid-connect/token" \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=password' \
  -d 'client_id=admin-cli' \
  -d 'username=admin' \
  -d 'password=admin' | jq -r '.access_token')

echo "ADMIN_TOKEN=$ADMIN_TOKEN"
echo

echo "Creating car-realm"
echo "-------------------------------"

curl -i -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"realm": "car-realm", "enabled": true, "registrationAllowed": true}'

echo "Creating car-api client"
echo "--------------------------"

CLIENT_ID=$(curl -si -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/clients" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"clientId": "car-api", "directAccessGrantsEnabled": true, "publicClient": true, "redirectUris": ["http://localhost:3000/*"]}' \
  | grep -oE '[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')

echo "CLIENT_ID=$CLIENT_ID"
echo

echo "Creating the client roles CARAPI_CREATE, CARAPI_UPDATE and CARAPI_DELETE for the car-api client"
echo "--------------------------------------------------------------"

curl -i -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/clients/$CLIENT_ID/roles" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "CARAPI_CREATE"}'

curl -i -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/clients/$CLIENT_ID/roles" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "CARAPI_UPDATE"}'

curl -i -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/clients/$CLIENT_ID/roles" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "CARAPI_DELETE"}'

CAR_CREATE_USER_CLIENT_ROLE_ID=$(curl -s "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/clients/$CLIENT_ID/roles/CARAPI_CREATE" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq -r '.id')

CAR_UPDATE_USER_CLIENT_ROLE_ID=$(curl -s "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/clients/$CLIENT_ID/roles/CARAPI_UPDATE" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq -r '.id')

CAR_DELETE_USER_CLIENT_ROLE_ID=$(curl -s "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/clients/$CLIENT_ID/roles/CARAPI_DELETE" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq -r '.id')

echo "CAR_CREATE_USER_CLIENT_ROLE_ID=$CAR_CREATE_USER_CLIENT_ROLE_ID"
echo "CAR_UPDATE_USER_CLIENT_ROLE_ID=$CAR_UPDATE_USER_CLIENT_ROLE_ID"
echo "CAR_DELETE_USER_CLIENT_ROLE_ID=$CAR_DELETE_USER_CLIENT_ROLE_ID"
echo

echo "Creating USERS group"
echo "--------------------"
USERS_GROUP_ID=$(curl -si -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/groups" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "USERS"}' \
  | grep -oE '[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')

echo "USERS_GROUP_ID=$USERS_GROUP_ID"
echo

echo "Creating ADMIN group"
echo "--------------------"
ADMINS_GROUP_ID=$(curl -si -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/groups" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "ADMINS"}' \
  | grep -oE '[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')

echo "ADMINS_GROUP_ID=$ADMINS_GROUP_ID"
echo

echo "Assigning CARAPI_CREATE, CARAPI_UPDATE and CARAPI_DELETE client roles to ADMINS group"
echo "-------------------------------------------------------------------"

curl -i -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/groups/$ADMINS_GROUP_ID/role-mappings/clients/$CLIENT_ID" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d "[{\"id\": \"$CAR_CREATE_USER_CLIENT_ROLE_ID\", \"name\": \"CARAPI_CREATE\"},
      {\"id\": \"$CAR_UPDATE_USER_CLIENT_ROLE_ID\", \"name\": \"CARAPI_UPDATE\"},
      {\"id\": \"$CAR_DELETE_USER_CLIENT_ROLE_ID\", \"name\": \"CARAPI_DELETE\"}]"

echo "Creating 'admin' user"
echo "---------------------"

ADMIN_ID=$(curl -si -X POST "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/users" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"username": "admin",
      "firstName": "Admin",
      "lastName": "Realm",
      "email": "change-me@hotmail.com",
      "emailVerified": true,
      "enabled": true,
      "credentials": [{"type": "password", "value": "car-api", "temporary": false}]}' \
  | grep -oE '[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')

echo "ADMIN_ID=$ADMIN_ID"
echo

echo "Assigning ADMINS group to admin"
echo "-------------------------------"

curl -i -X PUT "http://$KEYCLOAK_HOST_PORT/admin/realms/car-realm/users/$ADMIN_ID/groups/$ADMINS_GROUP_ID" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

echo "Getting admin access token"
echo "--------------------------"

curl -s -X POST "http://$KEYCLOAK_HOST_PORT/realms/car-realm/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin" \
  -d "password=car-api" \
  -d "grant_type=password" \
  -d "client_id=car-api" | jq -r .access_token
echo