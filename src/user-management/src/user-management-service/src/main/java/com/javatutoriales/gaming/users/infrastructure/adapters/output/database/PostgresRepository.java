package com.javatutoriales.gaming.users.infrastructure.adapters.output.database;

import com.javatutoriales.gaming.users.infrastructure.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgresRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
}
