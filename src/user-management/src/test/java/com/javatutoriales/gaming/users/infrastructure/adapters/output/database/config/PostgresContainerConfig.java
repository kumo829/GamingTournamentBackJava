package com.javatutoriales.gaming.users.infrastructure.adapters.output.database.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Deprecated // replaced by testcontainers test profile
@Testcontainers
@TestConfiguration
@Slf4j
@Profile("dbTest")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // disables the in memory H2 autoconfiguration
public class PostgresContainerConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    private static PostgreSQLContainer database = new PostgreSQLContainer<>("postgres:15.1-alpine");

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.datasource.driver-class-name", database::getDriverClassName);

        registry.add("spring.flyway.url", database::getJdbcUrl);
        registry.add("spring.flyway.user", database::getUsername);
        registry.add("spring.flyway.password", database::getPassword);
    }

    private static String url() {
        return String.format("jdbc:postgres://%s:%s/%s",
                database.getContainerIpAddress(),
                database.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                database.getDatabaseName());
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

    }
}
