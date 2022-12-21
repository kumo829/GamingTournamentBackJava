package com.javatutoriales.gaming.auth.application.usecases.register;

import com.javatutoriales.gaming.auth.domain.entities.Member;
import com.javatutoriales.gaming.auth.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.auth.domain.valueobjects.Profile;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Builder
public record RegisterAccountCommand(@Valid @NotNull Member member,
                                     @Valid @NotNull Credentials credentials,
                                     @Valid @NotNull Profile profile) {
    public static class RegisterAccountCommandBuilder {
        public RegisterAccountCommand build() {

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            RegisterAccountCommand command = new RegisterAccountCommand(member, credentials, profile);

            Set<ConstraintViolation<RegisterAccountCommand>> violations = validator.validate(command);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            return command;
        }
    }
}
