package com.javatutoriales.shared.domain.exception;

public class SpecificationException extends DomainException {
    public SpecificationException(String message, String errorCode) {
        super(message, errorCode);
    }

    public SpecificationException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public SpecificationException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }
}
