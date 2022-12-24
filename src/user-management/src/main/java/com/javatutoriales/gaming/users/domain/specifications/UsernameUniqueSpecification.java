package com.javatutoriales.gaming.users.domain.specifications;

import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.shared.domain.exception.SpecificationException;
import com.javatutoriales.shared.domain.specification.AbstractSpecification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class UsernameUniqueSpecification extends AbstractSpecification<Member> {

    @NonNull
    private final Stream<Member> existingMembers;

    @Override
    public boolean isSatisfiedBy(Member member) {
        return existingMembers.anyMatch(m -> m.getEmail().equalsIgnoreCase(member.getEmail()));
    }

    @Override
    public void check(Member member) throws SpecificationException {
        if (!isSatisfiedBy(member))
            throw new SpecificationException("Username already exists", "GES-001");
    }
}
