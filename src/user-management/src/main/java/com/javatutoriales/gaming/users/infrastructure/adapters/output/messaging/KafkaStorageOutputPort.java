package com.javatutoriales.gaming.users.infrastructure.adapters.output.messaging;

import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.events.AccountCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class KafkaStorageOutputPort implements AccountStorageOutputPort {
    @Override
    public Stream<Account> getAll() {
        return null;
    }

    @Override
    public Optional<Member> findMemberByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Account saveAccount(AccountCreatedEvent accountEvent) {
        return null;
    }
}
