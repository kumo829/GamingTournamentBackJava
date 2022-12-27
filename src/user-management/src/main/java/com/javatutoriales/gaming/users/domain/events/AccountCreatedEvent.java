package com.javatutoriales.gaming.users.domain.events;

import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.shared.domain.event.DomainEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;

@Data
@RequiredArgsConstructor
public class AccountCreatedEvent implements DomainEvent<Account> {
    private final Account account;
    private final ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of(UTC.getId()));
}
