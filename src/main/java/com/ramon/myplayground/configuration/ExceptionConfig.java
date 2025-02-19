package com.ramon.myplayground.configuration;

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

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleCustomException(RuntimeException ex, WebRequest request) {
        if (!(ex instanceof ProblemDetails problem)) {
            final var problemDetails = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
            problemDetails.setTitle("Unexpected Error");
            problemDetails.setDetail(ex.getMessage());
            return handleExceptionInternal(ex, problemDetails, new HttpHeaders(), HttpStatus.valueOf(problemDetails.getStatus()), request);
        }

        final var problemDetails = ProblemDetail.forStatus(problem.getStatus());
        problemDetails.setTitle(problem.getTitle());
        problemDetails.setDetail(problem.getDetail());
        problemDetails.setType(problem.getType());
        return handleExceptionInternal(ex, problemDetails, new HttpHeaders(), problem.getStatus(), request);
    }

}
