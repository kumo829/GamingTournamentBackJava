package com.javatutoriales.gaming.users.domain.exceptions;

import com.javatutoriales.shared.domain.exception.DomainException;
import lombok.Getter;

@Getter
public class InsecurePasswordException extends DomainException {
    private final String[] errors;

    public InsecurePasswordException(String message, String[] errors) {
        super(message, "IPE01");
        this.errors = errors;
    }
}
