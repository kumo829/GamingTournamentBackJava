package com.javatutoriales.shared.validations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Validator {
    public static void validateBean(Object object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        jakarta.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
