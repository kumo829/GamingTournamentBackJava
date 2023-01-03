package com.javatutoriales.gaming.users.domain.valueobjects;

import com.javatutoriales.shared.validations.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder(builderClassName = "CredentialsBuilder", buildMethodName = "unsafeBuild")
@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Credentials {
    @NotEmpty
    private final String username;
    @NotEmpty
    @Size(min = 8, max = 30)
    private final String password;

    public static class CredentialsBuilder {
        public Credentials build() {
            Credentials credentials = unsafeBuild();
            Validator.validateBean(credentials);

            return credentials;
        }
    }
}
