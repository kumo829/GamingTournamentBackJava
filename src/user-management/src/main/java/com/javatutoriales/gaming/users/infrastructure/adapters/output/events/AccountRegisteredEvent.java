package com.javatutoriales.gaming.users.infrastructure.adapters.output.events;

import com.javatutoriales.gaming.users.infrastructure.model.Account;
import com.javatutoriales.shared.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class AccountRegisteredEvent implements DomainEvent<Account> {
    private final Account account;
    private final ZonedDateTime creationDate = ZonedDateTime.now();
}
