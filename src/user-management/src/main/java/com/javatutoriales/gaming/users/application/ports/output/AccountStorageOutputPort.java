package com.javatutoriales.gaming.users.application.ports.output;

import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.events.AccountCreatedEvent;

import java.util.Optional;
import java.util.stream.Stream;

public interface AccountStorageOutputPort {
    Stream<Account> getAll();

    Optional<Account> findByUsername(String username);

    Account saveAccount(AccountCreatedEvent account);
}
