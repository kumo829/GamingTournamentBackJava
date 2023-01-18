package com.javatutoriales.gaming.users.domain.valueobjects;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

        @ParameterizedTest(name = "[{index}]: {0} - {1}")
        @MethodSource("credentialBuilderErrors")
        @DisplayName("fails if not all mandatory attributes are provided")
        void givenAnCredentialsBuilderWithNotAllRequiredAttributesProvided_whenInvokingTheBuildMethod_thenAnExceptionIsThrown(Credentials.CredentialsBuilder credentialsBuilder, String errorField, String errorMessage) {
            var ex = assertThrows(ConstraintViolationException.class, credentialsBuilder::build);
            assertThat(ex.getMessage()).contains(errorField).contains(errorMessage);
        }

        static Stream<Arguments> credentialBuilderErrors() {
            return Stream.of(
                    arguments(Credentials.builder().username("username"), "password", "must not be empty"),
                    arguments(Credentials.builder().username(""), "password", "must not be empty"),
                    arguments(Credentials.builder().password(""), "username", "must not be empty"),
                    arguments(Credentials.builder().password("password"), "username", "must not be empty"),
                    arguments(Credentials.builder().username("username").password("abc"), "password", "size must be between 8 and 60")
            );
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

        @Test
        @DisplayName("Password customizer modifies password")
        void givenAPasswordCustomizer_whenBuildTheCredentials_thenThePasswordShouldBeCustomized() {
            final String username = "username";
            final String originalPassword = "password";
            final String expectedPassword = "password";

            Credentials credentials = Credentials.builder()
                    .username(username)
                    .password(originalPassword)
                    .passwordCustomizer(p -> expectedPassword)
                    .build();

            assertThat(credentials.getUsername()).isEqualTo(username);
            assertThat(credentials.getPassword()).isEqualTo(expectedPassword);
        }
    }

    @Test
    @DisplayName("build toString is valid")
    void givenAValidCredentialsObject_whenInvokingToString_thenTheReturnedStringHasTheValidFormat() {
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