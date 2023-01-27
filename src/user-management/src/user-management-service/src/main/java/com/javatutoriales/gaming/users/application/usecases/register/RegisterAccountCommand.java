package com.javatutoriales.gaming.users.application.usecases.register;

import com.javatutoriales.gaming.users.commons.domain.valueobjects.Member;
import com.javatutoriales.gaming.users.commons.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.commons.domain.valueobjects.Profile;
import com.javatutoriales.shared.validations.Validator;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record RegisterAccountCommand(@Valid @NotNull Member member,
                                     @Valid @NotNull Credentials credentials,
                                     @Valid @NotNull Profile profile) {
    public static class RegisterAccountCommandBuilder {
        public RegisterAccountCommand build() {
            RegisterAccountCommand command = new RegisterAccountCommand(member, credentials, profile);

            Validator.validateBean(command);

            return command;
        }
    }
}
