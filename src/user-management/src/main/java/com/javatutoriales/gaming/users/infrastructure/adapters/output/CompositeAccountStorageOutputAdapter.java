package com.javatutoriales.gaming.users.infrastructure.adapters.output;

import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.events.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component("accountStorageOutputPort")
@RequiredArgsConstructor
public class CompositeAccountStorageOutputAdapter implements AccountStorageOutputPort {

    private final List<AccountStorageOutputPort> outputPorts;

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

        outputPorts.forEach(outputPort -> outputPort.saveAccount(accountEvent));

        return accountEvent.getAccount();
    }
}
