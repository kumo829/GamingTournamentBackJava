package com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services;

import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountUseCase;
import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.dto.RegisterAccountDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterAccountService {
    private final RegisterAccountUseCase registerAccountUseCase;

    public AccountId registerAccount(RegisterAccountDto accountDto) {
        return null;
    }
}
