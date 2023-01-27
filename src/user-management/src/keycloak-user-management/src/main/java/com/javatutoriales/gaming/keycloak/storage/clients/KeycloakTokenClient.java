package com.javatutoriales.gaming.keycloak.storage.clients;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PostExchange;

public interface KeycloakTokenClient {
    @PostExchange(url = "/realms/gaming-platform/protocol/openid-connect/token", contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE, accept = MediaType.APPLICATION_JSON_VALUE)
    TokenResponse getToken(@RequestParam("grant_type") String grantType,
                    @RequestParam("client_id") String clientId,
                    @RequestParam("client_secret") String clientSecret);
}
