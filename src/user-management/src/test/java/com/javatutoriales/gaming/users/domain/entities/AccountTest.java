package com.javatutoriales.gaming.users.domain.entities;

import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.domain.valueobjects.MemberId;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AccountTest {

    @Nested
    @DisplayName("Builder")
    class BuilderTest {

        @Test
        @DisplayName("Valid account when builder is used")
        void givenAnAccountBuildUsingTheBuilder_whenTheAccountIsBuild_thenAValidAccountIsReturned() {
            Account account = Account.builder()
                    .accountId(AccountId.withRandomId())
                    .credentials(Credentials.builder().username("username").password("password").build())
                    .member(Member.builder().memberId(MemberId.withId(UUID.randomUUID())).email("email@mail.com").firstName("firstName").lastName("lastName").build())
                    .profile(Profile.PARTICIPANT)
                    .build();

            assertThat(account.getId()).isNotNull();
            assertThat(account.getId().toString()).matches("^AccountId=[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$");

            assertThat(account.getCredentials()).isNotNull();
            assertThat(account.getMember()).isNotNull();
            assertThat(account.getProfile()).isNotNull();
        }


        @DisplayName("Required parameter not provided")
        @ParameterizedTest(name = "[{index}]: {0}")
        @MethodSource("buildersWithoutRequiredParameters")
        void givenARegisterAccountCommandBuilderWithoutOneRequiredParameter_whenBuildingTheRegisterAccountCommand_thenAConstraintViolationExceptionIsThrown(Account.AccountBuilder accountBuilder, String error) {
            assertThatThrownBy(accountBuilder::build).isInstanceOf(ConstraintViolationException.class)
                    .hasMessageContaining(error);
        }

        private static Stream<Arguments> buildersWithoutRequiredParameters() {
            return Stream.of(
                    arguments(named("AccountId not provided",
                                    Account.builder()
                                            .credentials(Credentials.builder().username("username").password("password").build())
                                            .member(Member.builder().memberId(MemberId.withId(UUID.randomUUID())).email("email@mail.com").firstName("firstName").lastName("lastName").build())
                                            .profile(Profile.PARTICIPANT)),
                            "id: must not be null"),

                    arguments(named("Credentials not provided",
                                    Account.builder()
                                            .accountId(AccountId.withRandomId())
                                            .member(Member.builder().memberId(MemberId.withId(UUID.randomUUID())).email("email@mail.com").firstName("firstName").lastName("lastName").build())
                                            .profile(Profile.PARTICIPANT)),
                            "credentials: must not be null"),

                    arguments(named("Member not provided",
                                    Account.builder()
                                            .accountId(AccountId.withRandomId())
                                            .credentials(Credentials.builder().username("username").password("password").build())
                                            .profile(Profile.PARTICIPANT)),
                            "member: must not be null"),


                    arguments(named("Profile not provided",
                                    Account.builder()
                                            .accountId(AccountId.withRandomId())
                                            .credentials(Credentials.builder().username("username").password("password").build())
                                            .member(Member.builder().memberId(MemberId.withId(UUID.randomUUID())).email("email@mail.com").firstName("firstName").lastName("lastName").build())),
                            "profile: must not be null")
            );
        }
    }
}