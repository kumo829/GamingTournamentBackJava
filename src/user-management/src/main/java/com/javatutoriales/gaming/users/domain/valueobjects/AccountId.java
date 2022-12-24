package com.javatutoriales.gaming.users.domain.valueobjects;

import com.javatutoriales.shared.domain.valueobject.BaseUUIDId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AccountId extends BaseUUIDId {

    private AccountId(UUID value) {
        super(value);
    }

    private AccountId(String value) {
        super(value);
    }

    public static AccountId withId(@NotBlank String id) {
        return new AccountId(id);
    }

    public static AccountId withId(@NotNull UUID uuid) {
        return new AccountId(uuid);
    }
}
