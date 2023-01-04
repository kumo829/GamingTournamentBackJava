package com.javatutoriales.gaming.users.domain.specifications;

import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.shared.domain.exception.SpecificationException;
import com.javatutoriales.shared.domain.specification.Specification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UsernameUniqueSpecification implements Specification<Member> {

    @NonNull
    private final Optional<Member> existingMember;

    @Override
    public boolean isSatisfiedBy(@NonNull Member member) {
        return existingMember.filter(m -> m.getEmail().equalsIgnoreCase(member.getEmail())).isPresent();
    }

    @Override
    public void check(@NonNull Member member) throws SpecificationException {
        if (isSatisfiedBy(member))
            throw new SpecificationException("Username '%s' already exists".formatted(member.getEmail()), "GES-001");
    }
}
