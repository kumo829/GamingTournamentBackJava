package com.javatutoriales.gaming.users.infrastructure.adapters.output.database;

import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.events.AccountCreatedEvent;
import com.javatutoriales.gaming.users.infrastructure.model.Account;
import com.javatutoriales.gaming.users.infrastructure.model.mappers.AccountMapper;
import com.javatutoriales.gaming.users.infrastructure.model.mappers.AccountMapperImpl;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("testcontainers")
@DataJpaTest
@ComponentScan({"com.javatutoriales.gaming.users.infrastructure.adapters.output.database", "com.javatutoriales.gaming.users.infrastructure.model", "com.javatutoriales.shared.domain.entity"})
class PostgresStorageOutputPortTest {

    private AccountMapper accountMapper = new AccountMapperImpl();

    @Autowired
    private TestEntityManager em;

    @Autowired
    private PostgresStorageOutputPort postgresOutputPort;

    @Test
    @DisplayName("Account can be fetched")
    void givenAnEmptyAccountsTable_whenInsertingANewAccount_thenTheAccountsMemberShouldBeFetchableFromTheTableAndContainTheTableValues() {

        var id = UUID.randomUUID();
        var email = "email@mail.com";
        var firstName = "firstName";
        var lastName = "lastName";

        em.persist(new Account(id, firstName, lastName, email));

        Optional<Member> maybeMember = postgresOutputPort.findMemberByUsername(email);

        assertThat(maybeMember).isNotEmpty();

        Member dbMember = maybeMember.get();
        assertThat(dbMember.getFirstName()).isEqualTo(firstName);
        assertThat(dbMember.getLastName()).isEqualTo(lastName);
        assertThat(dbMember.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Email is unique")
    void givenAnExistingRecordInAccountsTable_whenInsertingAnotherRecordWithTheSameEmail_thenAnErrorShouldBeRaised() {

        var email = "email@mail.com";

        em.persist(new Account(UUID.randomUUID(), "firstName", "lastName", email));
        em.flush();

        Account newAccount = new Account(UUID.randomUUID(), "anotherFirstName", "anotherLastName", email);
        var domainAccount = accountMapper.infraToDomain(newAccount);

        AccountCreatedEvent accountEvent = new AccountCreatedEvent(domainAccount);

        assertThatThrownBy(() -> {
            postgresOutputPort.saveAccount(accountEvent);
            em.flush();
        }).isInstanceOf(PersistenceException.class)
                .hasStackTraceContaining("accounts_email_key");
    }
}