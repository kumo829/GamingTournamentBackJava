package com.javatutoriales.gaming.users.infrastructure.adapters.input.api.handlers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ProblemDetail processConstraintViolationException(ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return buildFieldErrorsProblemDetail(errors);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = ex.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return handleExceptionInternal(ex, buildFieldErrorsProblemDetail(errors), headers, status, request);
    }

    private ProblemDetail buildFieldErrorsProblemDetail(Map<String, String> errors) {
        ProblemDetail fieldsProblemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "The provided parameters are invalid");
        fieldsProblemDetail.setTitle("Bad Request");
        fieldsProblemDetail.setType(URI.create("https://api.bookmarks.com/errors/not-found"));
        fieldsProblemDetail.setProperty("errorCategory", "Parameters");
        fieldsProblemDetail.setProperty("timestamp", Instant.now());
        fieldsProblemDetail.setProperty("fields", errors);

        return fieldsProblemDetail;
    }
}
