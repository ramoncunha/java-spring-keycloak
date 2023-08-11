package com.ramon.myplayground.infrastructure.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleCustomException(RuntimeException ex, WebRequest request) {
        if (!(ex instanceof ProblemDetails problem)) {
            return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }

        var problemDetails = ProblemDetail.forStatus(problem.getStatus());
        problemDetails.setTitle(problem.getTitle());
        problemDetails.setDetail(problem.getDetail());
        problemDetails.setType(problem.getType());
        return handleExceptionInternal(ex, problemDetails, new HttpHeaders(), problem.getStatus(), request);
    }

}
