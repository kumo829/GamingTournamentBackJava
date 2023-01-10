package com.javatutoriales.gaming.users.domain.exceptions;

import lombok.Getter;

@Getter
public class InsecurePasswordException extends RuntimeException {
    private final String[] errors;

    public InsecurePasswordException(String message, String[] errors) {
        super(message);
        this.errors = errors;
    }
}
