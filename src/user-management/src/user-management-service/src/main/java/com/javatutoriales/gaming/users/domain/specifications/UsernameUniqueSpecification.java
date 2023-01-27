package com.javatutoriales.gaming.users.domain.specifications;

import com.javatutoriales.gaming.users.commons.domain.entities.Account;
import com.javatutoriales.gaming.users.commons.domain.valueobjects.Member;
import com.javatutoriales.shared.domain.exception.SpecificationException;
import com.javatutoriales.shared.domain.specification.Specification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UsernameUniqueSpecification implements Specification<Member> {

    @NonNull
    private final Optional<Account> existingAccount;

    @Override
    public boolean isSatisfiedBy(@NonNull Member member) {
        return existingAccount.filter(a -> a.getMember().getEmail().equalsIgnoreCase(member.getEmail())).isPresent();
    }

    @Override
    public void check(@NonNull Member member) throws SpecificationException {
        if (isSatisfiedBy(member))
            throw new SpecificationException("Username '%s' already exists".formatted(member.getEmail()), "GES-001");
    }
}
