package com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.dto;

import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterAccountDto(@NotBlank String username,
                                 @NotBlank String password,
                                 @NotBlank String firstName,
                                 @NotBlank String lastName,
                                 @NotBlank String email,
                                 @NotNull Profile profile) {

}
