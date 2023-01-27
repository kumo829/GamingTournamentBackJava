package com.javatutoriales.gaming.users.application.usecases.register;

import com.javatutoriales.gaming.users.commons.domain.events.AccountCreatedEvent;
import com.javatutoriales.gaming.users.domain.exceptions.UsernameDuplicatedException;

public interface RegisterAccountUseCase {
     AccountCreatedEvent registerAccount(RegisterAccountCommand registerAccountCommand) throws UsernameDuplicatedException;
}