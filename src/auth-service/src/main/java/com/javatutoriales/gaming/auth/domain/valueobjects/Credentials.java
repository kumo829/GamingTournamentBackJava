package com.javatutoriales.gaming.auth.domain.valueobjects;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
@Builder
public class Credentials {
    @NotBlank
    final String username;
    @NotBlank
    final String password;
}
