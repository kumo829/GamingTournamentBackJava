package com.javatutoriales.gaming.users.domain.valueobjects;

import com.javatutoriales.shared.domain.valueobject.BaseUUIDId;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class AccountId extends BaseUUIDId {

    private AccountId(UUID value) {
        super(value);
    }

    private AccountId(String value) {
        super(value);
    }
    private AccountId() {
        super();
    }

    public static AccountId withId(@NotBlank UUID id) {
        return new AccountId(id);
    }

    public static AccountId withId(@NotBlank String id) {
        return new AccountId(id);
    }

    public static AccountId withRandomId() {
        return new AccountId();
    }

    @Override
    public String toString() {
        return String.format("AccountId=%s", getValue().toString());
    }
}
