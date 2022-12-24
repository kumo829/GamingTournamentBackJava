package com.javatutoriales.gaming.users.specifications;

import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.specifications.UsernameUniqueSpecification;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UsernameUniqueSpecificationTest {

    private UsernameUniqueSpecification specification;

    @Test
    void givenASpecificationWithEmptyMemberList_whenTheValidationIsApplied_thenTheResultShouldBeTrue() {
        Stream<Member> memberStream = Stream.empty();

        specification = new UsernameUniqueSpecification(memberStream);
        boolean memberExists = specification.isSatisfiedBy(Member.builder().email("member@email.com").build());
        assertThat(memberExists).isFalse();
    }

    @Test
    void givenASpecificationWithNonDuplicatedMemberList_whenTheValidationIsApplied_thenTheResultShouldBeFalse() {
        Stream<Member> memberStream = Stream.of(
                Member.builder().email("new.member@email.com").build(),
                Member.builder().email("other.member@email.com").build(),
                Member.builder().email("another.member@email.com").build());

        specification = new UsernameUniqueSpecification(memberStream);
        boolean memberExists = specification.isSatisfiedBy(Member.builder().email("member@email.com").build());
        assertThat(memberExists).isFalse();
    }

    @Test
    void givenASpecificationWithADuplicatedInTheMemberList_whenTheValidationIsApplied_thenTheResultShouldBeFalse() {
        Stream<Member> memberStream = Stream.of(
                Member.builder().email("member@email.com").build(),
                Member.builder().email("other.member@email.com").build(),
                Member.builder().email("another.member@email.com").build());

        specification = new UsernameUniqueSpecification(memberStream);
        boolean memberExists = specification.isSatisfiedBy(Member.builder().email("member@email.com").build());
        assertThat(memberExists).isTrue();
    }

}