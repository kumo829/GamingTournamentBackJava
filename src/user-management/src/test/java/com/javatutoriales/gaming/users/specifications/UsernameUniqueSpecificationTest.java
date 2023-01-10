package com.javatutoriales.gaming.users.specifications;

import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.specifications.UsernameUniqueSpecification;
import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.domain.valueobjects.Member;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.shared.domain.exception.SpecificationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UsernameUniqueSpecificationTest {

    private UsernameUniqueSpecification specification;

    @Test
    @DisplayName("Null member passed in constructor")
    void givenASpecificationInstanceWithANullMemberInTheConstructor_whenTheObjectIsConstructed_thenAndExceptionShouldBeThrown() {
        assertThatThrownBy(() -> new UsernameUniqueSpecification(null))
                .isInstanceOf(NullPointerException.class);
    }


    @Nested
    @DisplayName("isSatisfiedBy")
    class IsSatisfiedBy {
        @Test
        @DisplayName("Null parameter passed to isSatisfiedBy")
        void givenANullMember_whenIsSatisfiedByIsInvoked_thenAnExceptionShouldBeThrown() {
            Optional<Account> maybeAccount = Optional.empty();

            specification = new UsernameUniqueSpecification(maybeAccount);
            assertThatThrownBy(() -> specification.isSatisfiedBy(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("No other user with same email")
        void givenASpecificationWithEmptyMemberList_whenTheValidationIsApplied_thenTheResultShouldBeTrue() {
            Optional<Account> maybeAccount = Optional.empty();

            specification = new UsernameUniqueSpecification(maybeAccount);
            boolean accountExists = specification.isSatisfiedBy(getMember("member@email.com"));
            assertThat(accountExists).isFalse();
        }

        @Test
        @DisplayName("Wrong member passed as parameter")
        void givenASpecificationWithNonDuplicatedMemberList_whenTheValidationIsApplied_thenTheResultShouldBeFalse() {
            Optional<Account> maybeAccount = Optional.of(getAccount("other.member@email.com"));

            specification = new UsernameUniqueSpecification(maybeAccount);
            boolean accountExists = specification.isSatisfiedBy(getMember("member@email.com"));
            assertThat(accountExists).isFalse();
        }

        @Test
        @DisplayName("Existing member with same email")
        void givenASpecificationWithADuplicatedInTheMemberList_whenTheValidationIsApplied_thenTheResultShouldBeFalse() {
            Optional<Account> maybeAccount = Optional.of(getAccount("member@email.com"));

            specification = new UsernameUniqueSpecification(maybeAccount);
            boolean accountExists = specification.isSatisfiedBy(getMember("member@email.com"));
            assertThat(accountExists).isTrue();
        }
    }

    @Nested
    @DisplayName("check")
    class Check {
        @Test
        @DisplayName("Null parameter passed to check")
        void givenAnValidSpecification_whenCheckIsInvokedWithANullParameter_thenAnExceptionShouldBeThrown() {
            Optional<Account> maybeAccount = Optional.empty();

            specification = new UsernameUniqueSpecification(maybeAccount);
            assertThatThrownBy(() ->
                specification.check(null)
            ).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("No other user with same email")
        void givenASpecificationWithEmptyMemberList_whenTheCheckIsApplied_thenNoExceptionIsThrown() {
            Optional<Account> maybeAccount = Optional.empty();

            specification = new UsernameUniqueSpecification(maybeAccount);
            assertThatCode(() ->
                specification.check(getMember("member@email.com"))
            ).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Wrong account passed as parameter")
        void givenASpecificationWithNonDuplicatedMemberList_whenTheCheckIsApplied_thenNoExceptionIsThrown() {
            Optional<Account> maybeAccount = Optional.of(getAccount("other.member@email.com"));

            specification = new UsernameUniqueSpecification(maybeAccount);
            assertThatCode(() ->
                specification.check(getMember("member@email.com"))
            ).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Existing account with same email")
        void givenASpecificationWithADuplicatedInTheMemberList_whenTheCheckIsApplied_thenExceptionShouldBeThrown() {

            final String duplicatedUsername = "member@email.com";

            Optional<Account> maybeAccount = Optional.of(getAccount(duplicatedUsername));

            Member duplicatedAccount = getMember(duplicatedUsername);

            specification = new UsernameUniqueSpecification(maybeAccount);
            assertThatThrownBy(() -> specification.check(duplicatedAccount))
                    .isInstanceOf(SpecificationException.class)
                    .hasMessageContaining(duplicatedUsername)
                    .hasMessageContaining("already exists");
        }
    }

    private Member getMember(String email) {
        return Member.builder().email(email).firstName("firstName").lastName("lastName").build();
    }

    private Account getAccount(String email) {
        return Account.builder()
                .accountId(AccountId.withRandomId())
                .member(getMember(email))
                .profile(Profile.STAFF)
                .credentials(Credentials.builder().username("username").password("password").build())
                .build();
    }
}
