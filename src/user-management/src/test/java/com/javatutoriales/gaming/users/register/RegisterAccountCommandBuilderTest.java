package com.javatutoriales.gaming.users.register;

import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountCommand;
import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.domain.valueobjects.MemberId;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class RegisterAccountCommandBuilderTest {

    @DisplayName("RegisterAccountCommandBuilder")
    @ParameterizedTest(name = "[{index}]: {0}")
    @MethodSource("buildersWithoutRequiredParameters")
    void givenARegisterAccountCommandBuilderWithoutOneRequiredParameter_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrown(RegisterAccountCommand.RegisterAccountCommandBuilder command, String[] errors) {
        assertThatThrownBy(command::build).isInstanceOf(ConstraintViolationException.class)
            .hasMessageContainingAll(errors);
    }

    private static Stream<Arguments> buildersWithoutRequiredParameters() {
        return Stream.of(
                arguments(named("Member is null",
                            RegisterAccountCommand.builder()
                                .credentials(Credentials.builder().username("username").password("password").build())
                                .profile(Profile.PARTICIPANT)),
                            List.of("member", "must not be null").toArray(String[]::new)),

                arguments(named("Credentials is null",
                            RegisterAccountCommand.builder()
                                .member(Member.builder().firstName("firstName").lastName("lastName").email("user@email.com").build())
                                .profile(Profile.PARTICIPANT)),
                            List.of("credentials", "must not be null").toArray(String[]::new)),

                arguments(named("Profile is null",
                            RegisterAccountCommand.builder()
                                .member(Member.builder().firstName("firstName").lastName("lastName").email("user@email.com").build())
                                .credentials(Credentials.builder().username("username").password("password").build())),
                            List.of("profile", "must not be null").toArray(String[]::new)),

                arguments(named("Email format not valid",
                                RegisterAccountCommand.builder()
                                        .member(Member.builder().firstName("firstName").lastName("lastName").email("email").build())
                                        .credentials(Credentials.builder().username("username").password("password").build())
                                        .profile(Profile.PARTICIPANT)),
                            List.of("member.email", "must be a well-formed email address").toArray(String[]::new)),

                arguments(named("Member without first and last name",
                                RegisterAccountCommand.builder()
                                        .member(Member.builder().email("user@email.com").build())
                                        .credentials(Credentials.builder().username("username").password("password").build())
                                        .profile(Profile.PARTICIPANT)),
                            List.of("member.firstName", "member.lastName", "must not be empty").toArray(String[]::new))

        );
    }

    @Test
    @DisplayName("Valid RegisterAccountCommandBuilder")
    void givenARegisterAccountCommandWithValidInformation_whenBuildingTheRegisterAccountCommand_thenNoExceptionIsThrow() {

        assertThatCode(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().memberId(MemberId.withId(UUID.randomUUID())).firstName("firstName").lastName("lastName").email("user@email.com").build())
                    .credentials(Credentials.builder().username("username").password("password").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        }).doesNotThrowAnyException();
    }

}