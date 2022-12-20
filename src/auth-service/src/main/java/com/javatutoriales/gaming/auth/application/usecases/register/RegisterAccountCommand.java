package com.javatutoriales.gaming.auth.application.usecases.register;

import com.javatutoriales.gaming.auth.domain.entities.Member;
import com.javatutoriales.gaming.auth.domain.valueobjects.Credentials;
import lombok.*;

import javax.validation.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;


@Builder
public record RegisterAccountCommand(@Valid Member member, @Valid Credentials credentials, @NotBlank String profile) {
    public static class RegisterAccountCommandBuilder{
        public RegisterAccountCommand build(){

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            RegisterAccountCommand command = new RegisterAccountCommand(member, credentials, profile);



            Set<ConstraintViolation<RegisterAccountCommand>> violations = validator.validate(command);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            return command;
        }
        @Builder(builderMethodName = "member", buildMethodName = "buildMember")
        RegisterAccountCommandBuilder memberBuilder(String email) {
            return this;
        }

        @Builder(builderMethodName = "credentials", buildMethodName = "buildCredentials")
        RegisterAccountCommandBuilder credentialsBuilder(String username, String password) {
            return this;
        }

    }
}
