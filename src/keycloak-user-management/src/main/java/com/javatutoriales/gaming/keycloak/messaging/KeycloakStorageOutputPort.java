package com.javatutoriales.gaming.keycloak.messaging;

import com.javatutoriales.gaming.keycloak.storage.KeycloakOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakStorageOutputPort {

    private final KeycloakOutputPort keycloakOutputPort;

    @KafkaListener(topics = Topics.ACCOUNT_REGISTERED)
    public void listenAccountRegistered(String message){
        log.debug("Message received: {}", message);

        keycloakOutputPort.login();
    }
}
