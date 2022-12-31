package com.javatutoriales.gaming.users.infrastructure.adapters.config;

import com.javatutoriales.gaming.users.application.ports.input.RegisterAccountInputPort;
import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InputApiConfig {

    private final AccountStorageOutputPort accountStorageOutputPort;

    @Bean
    RegisterAccountUseCase registerAccountUseCase() {
        return new RegisterAccountInputPort(accountStorageOutputPort);
    }
}
