package com.javatutoriales.gaming.users.infrastructure.adapters.output.database;

import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.events.AccountCreatedEvent;
import com.javatutoriales.gaming.users.infrastructure.model.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component("dbStorageOutputPort")
@RequiredArgsConstructor
public class PostgresStorageOutputPort implements AccountStorageOutputPort {

    private final PotsgresRepository repository;
    private final AccountMapper accountMapper;
    @Override
    public Stream<Account> getAll() {
        return null;
    }

    @Override
    public Optional<Member> findMemberByUsername(String username) {

        Optional<com.javatutoriales.gaming.users.infrastructure.model.Account> maybeAccount = repository.findByEmail(username).or(Optional::empty);

        return maybeAccount.map(account -> accountMapper.infraToDomain(account).getMember());
    }

    @Override
    public Account saveAccount(AccountCreatedEvent accountEvent) {
        return accountMapper.infraToDomain(repository.save(accountMapper.domainToInfra(accountEvent.getAccount())));
    }
}
