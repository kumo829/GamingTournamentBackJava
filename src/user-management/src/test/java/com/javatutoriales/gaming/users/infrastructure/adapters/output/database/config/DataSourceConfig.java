package com.javatutoriales.gaming.users.infrastructure.adapters.output.database.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@TestConfiguration
@RequiredArgsConstructor
@Profile("dbTest")
public class DataSourceConfig {

    private final Environment env;

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(env.getRequiredProperty("spring.flyway.url"));
        dataSourceBuilder.username(env.getRequiredProperty("spring.flyway.user"));
        dataSourceBuilder.password(env.getRequiredProperty("spring.flyway.password"));
        return dataSourceBuilder.build();
    }
}