package com.javatutoriales.gaming.auth.domain.valueobjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Credentials {
    @NotEmpty
    private final String username;
    @NotEmpty
    @Size(min = 8, max = 30)
    private final String password;
}
