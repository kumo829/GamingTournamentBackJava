package com.javatutoriales.gaming.users.infrastructure.adapters.input.api;

import com.javatutoriales.gaming.users.commons.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.RegisterAccountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserManagementRestAPI {
    @Operation(summary = "Create a new member's account",
            description = "Create a new member's account in the site. This service receives all the parameters to create a valid account, including full name and credentials." +
                    "The service validates that a valid password is provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created",
                    content = { @Content(mediaType = "application/json",  schema = @Schema(implementation = RegisterAccountRequest.class)) }),
            @ApiResponse(responseCode = "405", description = "Invalid input") })
    ResponseEntity<AccountId> registerAccount(@Parameter(description = "account details", required = true) @Valid RegisterAccountRequest account);
}
