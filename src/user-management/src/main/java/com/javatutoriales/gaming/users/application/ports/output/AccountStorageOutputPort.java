package com.javatutoriales.gaming.users.application.ports.output;

import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.entities.Member;

import java.util.Optional;
import java.util.stream.Stream;

public interface AccountStorageOutputPort {
    Stream<Account> getAll();

    Optional<Member> findMemberByUsername(String username);

    Account saveAccount(Account account);
}
