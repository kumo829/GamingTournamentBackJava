package com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register;

import com.javatutoriales.gaming.users.commons.domain.valueobjects.Profile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterAccountRequest(@NotBlank String username,
                                     @NotBlank @Size(min = 8, max = 30) String password,
                                     @NotBlank String firstName,
                                     @NotBlank String lastName,
                                     @NotBlank @Email String email,
                                     @NotNull Profile profile) {

}
