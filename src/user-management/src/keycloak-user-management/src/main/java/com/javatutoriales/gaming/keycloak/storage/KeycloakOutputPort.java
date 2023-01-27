package com.javatutoriales.gaming.keycloak.storage;

import com.javatutoriales.gaming.keycloak.storage.clients.KeycloakTokenClient;
import com.javatutoriales.gaming.keycloak.storage.clients.KeycloakUsersClient;
import com.javatutoriales.gaming.keycloak.storage.clients.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakOutputPort {
    private final KeycloakTokenClient tokenClient;
    private final KeycloakUsersClient usersClient;

    private TokenResponse tokenResponse;

    public void login(){
        tokenResponse = tokenClient.getToken("client_credentials", "gaming-platform", "");
        usersClient.registerUser(null, tokenResponse.tokenType());
    }
}
