package com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services;

import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountUseCase;
import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.RegisterAccountRequest;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services.mappers.RegisterAccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterAccountService {
    private final RegisterAccountUseCase registerAccountUseCase;

    private final AccountStorageOutputPort accountStorageOutputPort;
    private final RegisterAccountMapper mapper;

    public AccountId registerAccount(RegisterAccountRequest request) {

        var registerAccountCommand = mapper.requestToCommand(request);

        var resultEvent = registerAccountUseCase.registerAccount(registerAccountCommand);
        accountStorageOutputPort.saveAccount(resultEvent.getAccount());

        return resultEvent.getAccount().getId();
    }
}
