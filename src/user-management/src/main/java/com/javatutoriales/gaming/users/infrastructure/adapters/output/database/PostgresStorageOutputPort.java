package com.javatutoriales.gaming.users.infrastructure.adapters.output.database;

import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.events.AccountCreatedEvent;
import com.javatutoriales.gaming.users.infrastructure.model.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PostgresStorageOutputPort implements AccountStorageOutputPort {

    private final PostgresRepository repository;
    private final AccountMapper accountMapper;
    @Override
    public Stream<Account> getAll() {
        return null;
    }

    @Override
    public Optional<Account> findByUsername(String username) {

        var maybeAccount = repository.findByEmail(username).or(Optional::empty);

        return maybeAccount.map(accountMapper::infraToDomain);
    }

    @Override
    public Account saveAccount(AccountCreatedEvent accountEvent) {

        var account = accountMapper.domainToInfra(accountEvent.getAccount());

        account.register();

        return accountMapper.infraToDomain(repository.save(account));
    }
}
