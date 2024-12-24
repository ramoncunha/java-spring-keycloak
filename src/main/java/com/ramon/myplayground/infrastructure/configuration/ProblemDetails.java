package com.ramon.myplayground.infrastructure.configuration;

import org.springframework.http.HttpStatus;

import java.net.URI;

public interface ProblemDetails {

    HttpStatus getStatus();

    String getTitle();

    String getDetail();

    URI getType();
}
