package com.ramon.myplayground.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramon.myplayground.auth.dto.credential;
import com.ramon.myplayground.auth.dto.UserRepresentation;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthGateway {
    public static final MediaType JSON = MediaType.get("application/json");
    public static final MediaType X_WWW_FORM_URLENCODED = MediaType.get("application/x-www-form-urlencoded");

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public void saveUser() {
        var token = getToken();
        var user = UserRepresentation.builder()
                .username("admin")
                .credentials(credential.builder()
                        .type("password")
                        .value("admin")
                        .temporary(false)
                        .build())
                .build();

        Request request;
        try {
            String json = objectMapper.writeValueAsString(user);
            var body = RequestBody.create(json, JSON);
            request = new Request.Builder()
                    .url("http://localhost:8443/admin/realms/car/users")
                    .post(body)
                    .header("Authorization", token.getTokenType() + " " + token.getAccessToken())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Token getToken() {
        var body = RequestBody.create("grant_type=password&client_id=carapi&client_secret=Z4Pq7qVAWZVNxNqB8XFRzeSsuJRhNa1g&username=teste&password=secret", X_WWW_FORM_URLENCODED);
        var request = new Request.Builder()
                .url("http://localhost:8443/realms/car/protocol/openid-connect/token")
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
