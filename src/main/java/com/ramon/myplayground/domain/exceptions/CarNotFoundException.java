package com.ramon.myplayground.domain.exceptions;

import com.ramon.myplayground.infrastructure.configuration.ProblemDetails;
import org.springframework.http.HttpStatus;

import java.net.URI;

public class CarNotFoundException extends RuntimeException implements ProblemDetails {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getTitle() {
        return "Car not found";
    }

    @Override
    public String getDetail() {
        return "Car not found";
    }

    @Override
    public URI getType() {
        return URI.create("any");
    }
}
