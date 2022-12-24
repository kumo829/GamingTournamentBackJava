package com.javatutoriales.gaming.users.application.usecases.register;

import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;

public interface RegisterAccountUseCase {
    AccountId registerAccount(RegisterAccountCommand registerAccountCommand);
}