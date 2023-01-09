package com.javatutoriales.gaming.users.infrastructure.adapters.output.messaging;

import com.javatutoriales.gaming.users.infrastructure.adapters.output.events.AccountRegisteredEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class KafkaStorageOutputPort {

    @TransactionalEventListener
    public void registerAccount(AccountRegisteredEvent accountRegisteredEvent) {
        // Not implemented yet
    }
}
