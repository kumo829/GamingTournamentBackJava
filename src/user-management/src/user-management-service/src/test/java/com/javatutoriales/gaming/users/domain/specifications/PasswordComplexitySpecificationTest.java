package com.javatutoriales.gaming.users.domain.specifications;


import com.javatutoriales.gaming.users.domain.exceptions.InsecurePasswordException;
import com.javatutoriales.gaming.users.commons.domain.valueobjects.Credentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PasswordComplexitySpecificationTest {
    protected PasswordComplexitySpecification specification;

    @BeforeEach
    void setUp() {
        specification = new PasswordComplexitySpecification();
    }

    @Nested
    @DisplayName("isSatisfiedBy")
    class IsSatisfiedBy {
        @Test
        @DisplayName("Null credentials throw an exception")
        void givenANullCredentials_whenIsSatisfiedByIsInvoked_thenAnExceptionShouldBeThrown() {
            Assertions.assertThatThrownBy(() -> specification.isSatisfiedBy(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Secure password is valid")
        void givenASecurePassword_whenIsSatisfiedByIsInvoked_thenTheResultShouldBeTrue() {
            var credentials = Credentials.builder().username("administrator").password("aK0P#$fiLP-.sRLK0#4").build();

            assertThat(specification.isSatisfiedBy(credentials)).isTrue();
        }

        @ParameterizedTest
        @MethodSource("insecurePasswords")
        void givenAnInsecurePassword_whenIsSatisfiedByIsInvoked_thenTheResultShouldBeFalse(String password){
            var credentials = Credentials.builder().username("administrator").password(password).build();

            assertThat(specification.isSatisfiedBy(credentials)).isFalse();
        }

        private static Stream<Arguments> insecurePasswords() {
            return PasswordComplexitySpecificationTest.insecurePasswords();
        }
    }

    @Nested
    @DisplayName("check")
    class Check {
        @Test
        @DisplayName("Null credentials passed to check")
        void givenANullCredentials_whenCheckIsInvoked_thenAnExceptionShouldBeThrown() {
            Assertions.assertThatThrownBy(() ->
                specification.check(null)
            ).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Secure password doesn't throw an exception")
        void givenASecurePassword_whenCheckIsInvoked_thenNoExceptionIsThrown() {
            var credentials = Credentials.builder().username("administrator").password("aK0P#$fiLP-.sRLK0#4").build();

            AssertionsForClassTypes.assertThatCode( () ->
                    specification.check(credentials)
            ).doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource("insecurePasswords")
        void givenAnInsecurePassword_whenCheckIsInvoked_thenAnExceptionIsThrown(String password) {
            var credentials = Credentials.builder().username("administrator").password(password).build();

            Assertions.assertThatThrownBy(() -> specification.check(credentials))
                    .isInstanceOf(InsecurePasswordException.class);
        }

        private static Stream<Arguments> insecurePasswords() {
            return PasswordComplexitySpecificationTest.insecurePasswords();
        }
    }

    private static Stream<Arguments> insecurePasswords() {
        return Stream.of(
                Arguments.arguments(Named.named("only lowercase", "abcdefgh")),
                Arguments.arguments(Named.named("only uppercase", "ABCDEFGH")),
                Arguments.arguments(Named.named("only numbers", "12345678")),
                Arguments.arguments(Named.named("only special", "!@#$%^&*.")),

                Arguments.arguments(Named.named("only uppercase and lowercase", "aBcDeFgH")),
                Arguments.arguments(Named.named("only numbers and specials", "1@3$5^7*")),

                Arguments.arguments(Named.named("only uppercase, lowercase and numbers", "aB3dE6fG9")),

                Arguments.arguments(Named.named("including qwerty", "a03OLs#s)qwerty-")),
                Arguments.arguments(Named.named("including sequence of numbers", "a03OLs#s)123456-")),

                Arguments.arguments(Named.named("including username", "a03OLs#s)administrator3LO-"))

        );
    }
}