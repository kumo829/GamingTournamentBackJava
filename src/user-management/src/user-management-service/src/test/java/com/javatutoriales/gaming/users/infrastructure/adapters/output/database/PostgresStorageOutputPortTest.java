package com.javatutoriales.gaming.users.infrastructure.adapters.output.database;

import com.javatutoriales.gaming.users.domain.valueobjects.Member;
import com.javatutoriales.gaming.users.domain.events.AccountCreatedEvent;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.gaming.users.infrastructure.adapters.config.JpaAuditingConfiguration;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("testcontainers")
@DataJpaTest
@Import(JpaAuditingConfiguration.class)
@ComponentScan({"com.javatutoriales.gaming.users.infrastructure.adapters.output.database",
        "com.javatutoriales.gaming.users.infrastructure.model",
        "com.javatutoriales.shared.domain.entity"})
class PostgresStorageOutputPortTest {

    private AccountMapper accountMapper = new AccountMapperImpl();

    @Autowired
    private TestEntityManager em;

    @Autowired
    private PostgresStorageOutputPort postgresOutputPort;

    @Test
    @DisplayName("Account can be fetched")
    void givenAnEmptyAccountsTable_whenInsertingANewAccount_thenTheAccountsShouldBeFetchedFromTheTableAndContainTheTableValues() {
        var id = UUID.randomUUID();
        var email = "email@mail.com";
        var firstName = "firstName";
        var lastName = "lastName";
        var username = "username";
        var password = "password";
        var profile = Profile.STAFF;

        em.persist(new Account(id, firstName, lastName, email, username, password, profile));

        var maybeAccount = postgresOutputPort.findByUsername(email);

        assertThat(maybeAccount).isNotEmpty();
        assertThat(maybeAccount.get().getMember()).isNotNull();

        Member dbMember = maybeAccount.get().getMember();
        assertThat(dbMember.getFirstName()).isEqualTo(firstName);
        assertThat(dbMember.getLastName()).isEqualTo(lastName);
        assertThat(dbMember.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Email is unique")
    void givenAnExistingRecordInAccountsTable_whenInsertingAnotherRecordWithTheSameEmail_thenAnErrorShouldBeRaised() {
        var email = "email@mail.com";
        var username = "username";
        var password = "password";
        var profile = Profile.STAFF;

        em.persist(new Account(UUID.randomUUID(), "firstName", "lastName", email, username, password, profile));
        em.flush();

        Account newAccount = new Account(UUID.randomUUID(), "anotherFirstName", "anotherLastName", email, username, password, profile);
        var domainAccount = accountMapper.infraToDomain(newAccount);

        AccountCreatedEvent accountEvent = new AccountCreatedEvent(domainAccount);

        postgresOutputPort.saveAccount(accountEvent);
        Assertions.assertThatThrownBy(() -> {
            em.flush();
        }).isInstanceOf(PersistenceException.class)
                .hasStackTraceContaining("accounts_email_key");
    }
}