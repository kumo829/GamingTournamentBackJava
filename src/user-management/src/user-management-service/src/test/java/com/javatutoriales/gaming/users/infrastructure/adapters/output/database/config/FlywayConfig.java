package com.javatutoriales.gaming.users.infrastructure.adapters.output.database.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Deprecated // replaced by testcontainers test profile
@TestConfiguration
@RequiredArgsConstructor
@Slf4j
@Profile("dbTest")
public class FlywayConfig {

    private final Environment env;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        log.info("Performing flyway migration....");
        return new Flyway(Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                        env.getRequiredProperty("spring.flyway.url"),
                        env.getRequiredProperty("spring.flyway.user"),
                        env.getRequiredProperty("spring.flyway.password"))
                .locations("filesystem:./src/main/db/migration")
        );
    }
}