package com.javatutoriales.gaming.users.domain.specifications;

import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.shared.domain.exception.SpecificationException;
import com.javatutoriales.shared.domain.specification.Specification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UsernameUniqueSpecification implements Specification<Account> {

    @NonNull
    private final Optional<Account> existingAccount;

    @Override
    public boolean isSatisfiedBy(@NonNull Account account) {
        return existingAccount.filter(a -> a.getMember().getEmail().equalsIgnoreCase(account.getMember().getEmail())).isPresent();
    }

    @Override
    public void check(@NonNull Account account) throws SpecificationException {
        if (isSatisfiedBy(account))
            throw new SpecificationException("Username '%s' already exists".formatted(account.getMember().getEmail()), "GES-001");
    }
}
