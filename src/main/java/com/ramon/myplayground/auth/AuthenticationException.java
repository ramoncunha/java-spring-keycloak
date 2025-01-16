package com.ramon.myplayground.auth;

import com.ramon.myplayground.configuration.ProblemDetails;
import org.springframework.http.HttpStatus;

import java.net.URI;

public class AuthenticationException extends RuntimeException implements ProblemDetails {

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    @Override
    public String getTitle() {
        return "Authentication failed, try again later";
    }

    @Override
    public String getDetail() {
        if (getCause() != null) {
            return getCause().getMessage();
        }
        return getMessage();
    }

    @Override
    public URI getType() {
        return URI.create("sample:error:authentication-failed");
    }
}
