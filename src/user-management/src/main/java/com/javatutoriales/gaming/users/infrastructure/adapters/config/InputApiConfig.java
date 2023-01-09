package com.javatutoriales.gaming.users.infrastructure.adapters.config;

import com.javatutoriales.gaming.users.application.ports.input.RegisterAccountInputPort;
import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountUseCase;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services.mappers.RegisterAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InputApiConfig {

    @Qualifier("dbStorageOutputPort")
    private final AccountStorageOutputPort dbStorageOutputPort;

    private final RegisterAccountMapper accountMapper;

    @Bean
    RegisterAccountUseCase registerAccountUseCase() {
        return new RegisterAccountInputPort(dbStorageOutputPort, accountMapper);
    }
}
