package com.javatutoriales.gaming.keycloak.storage.clients;


import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.PostExchange;

public interface KeycloakUsersClient {
    @PostExchange(url = "/admin/realms/gaming-platform/users", contentType = MediaType.APPLICATION_JSON_VALUE, accept = MediaType.APPLICATION_JSON_VALUE)
    String registerUser(@RequestBody UserRepresentation userRepresentation, @RequestHeader("Authentication") String token);
}
