package com.javatutoriales.gaming.users.register;

import com.javatutoriales.gaming.auth.domain.entities.Member;
import com.javatutoriales.gaming.auth.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.auth.domain.valueobjects.Profile;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class RegisterAccountCommandBuilderTest {
    @Test
    @DisplayName("Member not null")
    void givenARegisterAccountCommandWithoutMember_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .credentials(Credentials.builder().username("username").password("password").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("member")
                .hasMessageContaining("must not be null");
    }

    @Test
    @DisplayName("Credentials not null")
    void givenARegisterAccountCommandWithoutCredentials_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().firstName("firstName").lastName("lastName").email("user@email.com").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("credentials")
                .hasMessageContaining("must not be null");
    }

    @Test
    @DisplayName("Profile not null")
    void givenARegisterAccountCommandWithoutProfile_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().firstName("firstName").lastName("lastName").email("user@email.com").build())
                    .credentials(Credentials.builder().username("username").password("password").build())
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("profile")
                .hasMessageContaining("must not be null");
    }

    @Test
    @DisplayName("Email format not valid")
    void givenARegisterAccountCommandWithMemberEmailWithWrongFormat_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().firstName("firstName").lastName("lastName").email("email").build())
                    .credentials(Credentials.builder().username("username").password("password").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("member.email")
                .hasMessageContaining("must be a well-formed email address");
    }

    @Test
    @DisplayName("Password not long enough")
    void givenARegisterAccountCommandWithMemberShortPassword_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().firstName("firstName").lastName("lastName").email("user@email.com").build())
                    .credentials(Credentials.builder().username("username").password("pass").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("credentials.password")
                .hasMessageContaining("size must be between 8 and 30");
    }

    @Test
    @DisplayName("Member without first and last name")
    void givenARegisterAccountCommandWithoutMemberFirstNameAndLastName_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().email("user@email.com").build())
                    .credentials(Credentials.builder().username("username").password("password").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("member.firstName")
                .hasMessageContaining("member.lastName")
                .hasMessageContaining("must not be empty");
    }

    @Test
    @DisplayName("Member without last name")
    void givenARegisterAccountCommandWithoutMemberLastName_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().firstName("firstName").email("user@email.com").build())
                    .credentials(Credentials.builder().username("username").password("password").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("member.lastName")
                .hasMessageContaining("must not be empty");
    }
    @Test
    @DisplayName("Credentials without username")
    void givenARegisterAccountCommandWithoutCredentialUsername_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().firstName("firstName").lastName("lastName").email("user@email.com").build())
                    .credentials(Credentials.builder().password("password").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("credentials.username")
                .hasMessageContaining("must not be empty");
    }

    @Test
    @DisplayName("Credentials without password")
    void givenARegisterAccountCommandWithoutCredentialPassword_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrow() {
        assertThatThrownBy(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().firstName("firstName").lastName("lastName").email("user@email.com").build())
                    .credentials(Credentials.builder().username("username").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        })
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("credentials.password")
                .hasMessageContaining("must not be empty");
    }
    @Test
    @DisplayName("Valid RegisterAccountCommandBuilder")
    void givenARegisterAccountCommandWithValidInformation_whenBuildingTheRegisterAccountCommand_thenNoExceptionIsThrow() {

        assertThatCode(() -> {
            RegisterAccountCommand command = RegisterAccountCommand.builder()
                    .member(Member.builder().firstName("firstName").lastName("lastName").email("user@email.com").build())
                    .credentials(Credentials.builder().username("username").password("password").build())
                    .profile(Profile.PARTICIPANT)
                    .build();
        }).doesNotThrowAnyException();
    }

}