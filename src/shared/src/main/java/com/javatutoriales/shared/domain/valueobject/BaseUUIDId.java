package com.javatutoriales.shared.domain.valueobject;

import java.util.UUID;

public abstract class BaseUUIDId extends BaseId<UUID> {
    protected BaseUUIDId(UUID value) {
        super(value);
    }

    protected BaseUUIDId(String value) {
        super(UUID.fromString(value));
    }

    protected BaseUUIDId() {
        this(UUID.randomUUID());
    }
}
