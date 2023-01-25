package com.javatutoriales.gaming.keycloak.storage;

import com.javatutoriales.gaming.keycloak.storage.clients.KeycloakTokenClient;
import com.javatutoriales.gaming.keycloak.storage.clients.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakOutputPort {
    private final KeycloakTokenClient tokenClient;

    private TokenResponse token;

    public void login(){
        token = tokenClient.getToken("client_credentials", "gaming-platform", "");
    }
}
