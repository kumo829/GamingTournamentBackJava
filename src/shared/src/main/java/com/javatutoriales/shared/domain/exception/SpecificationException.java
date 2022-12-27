package com.javatutoriales.shared.domain.exception;

public class SpecificationException extends DomainException {
    public SpecificationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
