package com.javatutoriales.gaming.auth.application.usecases.register;

import com.javatutoriales.gaming.auth.domain.valueobjects.AccountId;

public interface RegisterAccountUseCase {
    AccountId registerAccount(RegisterAccountCommand registerAccountCommand);
}