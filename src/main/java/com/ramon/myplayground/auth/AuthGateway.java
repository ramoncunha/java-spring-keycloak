package com.ramon.myplayground.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramon.myplayground.auth.dto.CredentialRepresentation;
import com.ramon.myplayground.auth.dto.Token;
import com.ramon.myplayground.auth.dto.UserRepresentation;
import com.ramon.myplayground.user.dto.UserRequest;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthGateway {
    public static final MediaType JSON = MediaType.get("application/json");
    public static final MediaType X_WWW_FORM_URLENCODED = MediaType.get("application/x-www-form-urlencoded");

    private final String keycloakUrl;
    private final String keycloakRealm;
    private final String keycloakClientId;
    private final String keycloakClientSecret;
    private final String keycloakUsername;
    private final String keycloakPassword;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public AuthGateway(@Value("${keycloak.host}") String keycloakUrl,
                       @Value("${keycloak.realm}") String keycloakRealm,
                       @Value("${keycloak.clientId}") String keycloakClientId,
                       @Value("${keycloak.clientSecret}") String keycloakClientSecret,
                       @Value("${keycloak.username}") String keycloakUsername,
                       @Value("${keycloak.password}") String keycloakPassword,
                       OkHttpClient client,
                       ObjectMapper objectMapper) {
        this.keycloakUrl = keycloakUrl;
        this.keycloakRealm = keycloakRealm;
        this.keycloakClientId = keycloakClientId;
        this.keycloakClientSecret = keycloakClientSecret;
        this.keycloakUsername = keycloakUsername;
        this.keycloakPassword = keycloakPassword;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("preview")
    public void saveUser(UserRequest userRequest) {
        var token = getToken();
        var user = UserRepresentation.builder()
                .username(userRequest.email())
                .firstName("firstName")
                .lastName("lastName")
                .email(userRequest.email())
                .emailVerified(true)
                .enabled(true)
                .credentials(List.of(CredentialRepresentation.builder()
                        .type("password")
                        .value(userRequest.password())
                        .temporary(false)
                        .build()))
                .build();

        Request request;
        try {
            String json = objectMapper.writeValueAsString(user);
            var body = RequestBody.create(json, JSON);
            request = new Request.Builder()
                    .url(STR."\{keycloakUrl}/admin/realms/\{keycloakRealm}/users")
                    .post(body)
                    .header("Authorization", STR."\{token.getTokenType()} \{token.getAccessToken()}")
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try (Response response = client.newCall(request).execute()) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("preview")
    public Token getToken() {
        var body = RequestBody.create(STR."""
        grant_type=password&
        client_id=\{keycloakClientId}&
        client_secret=\{keycloakClientSecret}
        &username=\{keycloakUsername}&
        password=\{keycloakPassword}
        """, X_WWW_FORM_URLENCODED);

        var request = new Request.Builder()
                .url(STR."\{keycloakUrl}/realms/\{keycloakRealm}/protocol/openid-connect/token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            var responseAsString = response.body().string();
            return objectMapper.readValue(responseAsString, Token.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
