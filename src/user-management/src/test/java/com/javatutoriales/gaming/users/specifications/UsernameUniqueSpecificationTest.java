package com.javatutoriales.gaming.users.specifications;

import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.specifications.UsernameUniqueSpecification;
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
        assertThatThrownBy(() -> {
            new UsernameUniqueSpecification(null);
        }).isInstanceOf(NullPointerException.class);
    }


    @Nested
    @DisplayName("isSatisfiedBy")
    class IsSatisfiedBy {
        @Test
        @DisplayName("Null parameter passed to isSatisfiedBy")
        void givenAnValidSpecification_whenIsSatisfiedByIsInvokedWithANullParameter_thenAnExceptionShouldBeThrown() {
            Optional<Member> maybeMember = Optional.empty();

            specification = new UsernameUniqueSpecification(maybeMember);
            assertThatThrownBy(() -> {
                specification.isSatisfiedBy(null);
            }).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("No other user with same email")
        void givenASpecificationWithEmptyMemberList_whenTheValidationIsApplied_thenTheResultShouldBeTrue() {
            Optional<Member> maybeMember = Optional.empty();

            specification = new UsernameUniqueSpecification(maybeMember);
            boolean memberExists = specification.isSatisfiedBy(Member.builder().email("member@email.com").build());
            assertThat(memberExists).isFalse();
        }

        @Test
        @DisplayName("Wrong member passed as parameter")
        void givenASpecificationWithNonDuplicatedMemberList_whenTheValidationIsApplied_thenTheResultShouldBeFalse() {
            Optional<Member> maybeMember = Optional.of(Member.builder().email("other.member@email.com").build());

            specification = new UsernameUniqueSpecification(maybeMember);
            boolean memberExists = specification.isSatisfiedBy(Member.builder().email("member@email.com").build());
            assertThat(memberExists).isFalse();
        }

        @Test
        @DisplayName("Existing member with same email")
        void givenASpecificationWithADuplicatedInTheMemberList_whenTheValidationIsApplied_thenTheResultShouldBeFalse() {
            Optional<Member> maybeMember = Optional.of(Member.builder().email("member@email.com").build());

            specification = new UsernameUniqueSpecification(maybeMember);
            boolean memberExists = specification.isSatisfiedBy(Member.builder().email("member@email.com").build());
            assertThat(memberExists).isTrue();
        }
    }

    @Nested
    @DisplayName("check")
    class Check {
        @Test
        @DisplayName("Null parameter passed to check")
        void givenAnValidSpecification_whenCheckIsInvokedWithANullParameter_thenAnExceptionShouldBeThrown() {
            Optional<Member> maybeMember = Optional.empty();

            specification = new UsernameUniqueSpecification(maybeMember);
            assertThatThrownBy(() -> {
                specification.check(null);
            }).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("No other user with same email")
        void givenASpecificationWithEmptyMemberList_whenTheCheckIsApplied_thenNoExceptionIsThrown() {
            Optional<Member> maybeMember = Optional.empty();

            specification = new UsernameUniqueSpecification(maybeMember);
            assertThatCode(() -> {
                specification.check(Member.builder().email("member@email.com").build());
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Wrong member passed as parameter")
        void givenASpecificationWithNonDuplicatedMemberList_whenTheCheckIsApplied_thenNoExceptionIsThrown() {
            Optional<Member> maybeMember = Optional.of(Member.builder().email("other.member@email.com").build());

            specification = new UsernameUniqueSpecification(maybeMember);
            assertThatCode(() -> {
                specification.check(Member.builder().email("member@email.com").build());
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Existing member with same email")
        void givenASpecificationWithADuplicatedInTheMemberList_whenTheCheckIsApplied_thenExceptionShouldBeThrown() {

            final String duplicatedUsername = "member@email.com";

            Optional<Member> maybeMember = Optional.of(Member.builder().email(duplicatedUsername).build());

            Member duplicatedMember = Member.builder().email(duplicatedUsername).build();

            specification = new UsernameUniqueSpecification(maybeMember);
            assertThatThrownBy(() -> specification.check(duplicatedMember))
                    .isInstanceOf(SpecificationException.class)
                    .hasMessageContaining(duplicatedUsername)
                    .hasMessageContaining("already exists");
        }
    }
}
