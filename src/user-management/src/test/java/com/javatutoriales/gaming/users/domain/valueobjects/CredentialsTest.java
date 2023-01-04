package com.javatutoriales.gaming.users.domain.valueobjects;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CredentialsTest {
    @Nested
    @DisplayName("Builder")
    class BuilderTest {
        @Test
        @DisplayName("Builder is valid")
        void givenACredentialsObject_whenInvokingTheBuilderMethod_thenABuilderObjectShouldBeProvided() {
            Credentials.CredentialsBuilder credentialsBuilder = Credentials.builder();
            assertThat(credentialsBuilder).isNotNull();
        }

        @Test
        @DisplayName("Builder returns a credentials object if all mandatory attributes are valid and provided")
        void givenACredentialsBuilderWithAllRequiredFields_whenInvokingTheBuildersBuildMethod_thenACredentialsObjectShouldBeReturned() {
            var credentials = Credentials.builder().username("username").password("password").build();

            assertThat(credentials).isNotNull().isOfAnyClassIn(Credentials.class);
        }

        @Test
        @DisplayName("fails if not all mandatory attributes are provided")
        void givenAnCredentialsBuilderWithNotAllRequiredAttributesProvided_whenInvokingTheBuildMethod_thenAnExceptionIsThrown() {

            var ex = assertThrows(ConstraintViolationException.class, () -> Credentials.builder().username("username").build());
            assertThat(ex.getMessage()).contains("password").contains("must not be empty");

            ex = assertThrows(ConstraintViolationException.class, () -> Credentials.builder().username("").build());
            assertThat(ex.getMessage()).contains("password").contains("must not be empty");


            ex = assertThrows(ConstraintViolationException.class, () -> Credentials.builder().password("").build());
            assertThat(ex.getMessage()).contains("username").contains("must not be empty");

            ex = assertThrows(ConstraintViolationException.class, () -> Credentials.builder().password("password").build());
            assertThat(ex.getMessage()).contains("username").contains("must not be empty");


            ex = assertThrows(ConstraintViolationException.class, () -> Credentials.builder().username("username").password("abc").build());
            assertThat(ex.getMessage()).contains("password").contains("size must be between 8 and 30");
        }


        @Test
        @DisplayName("Builder set the credential's attributes")
        void givenACredentialsObjectConstructedUsingItsBuilder_whenReadingItsProperties_thenValuesShouldBeEqualsToTheProvidedInTheBuilder() {

            final String expectedUsername = "username";
            final String expectedPassword = "password";

            Credentials credentials = buildCredentials(expectedUsername, expectedPassword);

            assertThat(credentials.getUsername()).isEqualTo(expectedUsername);
            assertThat(credentials.getPassword()).isEqualTo(expectedPassword);
        }
    }

    @Test
    @DisplayName("build toString is valid")
    void given_when_then() {
        final String expectedUsername = "username";
        final String expectedPassword = "password";

        Credentials credentials = buildCredentials(expectedUsername, expectedPassword);

        assertThat(credentials).hasToString("Credentials(username=%s, password=%s)".formatted(expectedUsername, expectedPassword));
    }

    private Credentials buildCredentials(final String username, final String password) {
        return Credentials.builder()
                .username(username)
                .password(password)
                .build();
    }
}