package com.javatutoriales.gaming.users.commons.domain.valueobjects;

import com.javatutoriales.shared.validations.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.function.UnaryOperator;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Credentials {
    @NotEmpty
    private final String username;
    @NotEmpty
    @Size(min = 8, max = 60)
    private String password;

    @Builder(builderClassName = "CredentialsBuilder")
    private Credentials(String username, String password, UnaryOperator<String> passwordCustomizer) {
        this.username = username;
        this.password = password;

        Validator.validateBean(this);

        this.password = passwordCustomizer == null ? password : passwordCustomizer.apply(password);
    }
}
