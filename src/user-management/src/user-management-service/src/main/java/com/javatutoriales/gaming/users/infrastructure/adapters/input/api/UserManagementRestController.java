package com.javatutoriales.gaming.users.infrastructure.adapters.input.api;

import com.javatutoriales.gaming.users.commons.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services.RegisterAccountService;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.RegisterAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserManagementRestController implements UserManagementRestAPI{

    private final RegisterAccountService registerAccountService;

    @Override
    @PostMapping
    public ResponseEntity<AccountId> registerAccount(@RequestBody RegisterAccountRequest accountDto) {
        var response = registerAccountService.registerAccount(accountDto);

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        URI newUri = builder.path("/{accountId}").build(response.getValue());

        return ResponseEntity.created(newUri).body(response);
    }
}
