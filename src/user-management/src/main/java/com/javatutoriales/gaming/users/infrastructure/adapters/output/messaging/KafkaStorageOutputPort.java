package com.javatutoriales.gaming.users.infrastructure.adapters.output.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatutoriales.gaming.users.infrastructure.adapters.output.events.AccountRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaStorageOutputPort {

    private final KafkaTemplate<UUID, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @TransactionalEventListener
    public void registerAccount(AccountRegisteredEvent accountRegisteredEvent) throws JsonProcessingException {
        var key = accountRegisteredEvent.getAccount().getId();

        kafkaTemplate.send(Topics.ACCOUNT_REGISTERED, key, objectMapper.writeValueAsString(accountRegisteredEvent));
    }
}
