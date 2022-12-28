package com.javatutoriales.gaming.users.infrastructure.adapters.input.api;

import com.javatutoriales.gaming.users.domain.entities.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserManagementRestController implements UserManagementRestAPI{
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        return null;
    }
}
