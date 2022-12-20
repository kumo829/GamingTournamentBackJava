package com.javatutoriales.gaming.auth.domain.exceptions;

import com.javatutoriales.shared.domain.exception.DomainException;

public class UsernameDuplicatedException extends DomainException {
    public UsernameDuplicatedException(String message, String errorCode) {
        super(message, errorCode);
    }
}
