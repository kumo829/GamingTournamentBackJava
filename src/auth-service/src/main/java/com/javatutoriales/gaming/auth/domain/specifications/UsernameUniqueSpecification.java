package com.javatutoriales.gaming.auth.domain.specifications;

import com.javatutoriales.gaming.auth.domain.entities.Member;
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
    public String message() {
        return "Username already exists";
    }
}
