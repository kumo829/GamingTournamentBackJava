package com.javatutoriales.gaming.users.domain.valueobjects;

import com.javatutoriales.shared.domain.valueobject.BaseUUIDId;

import java.util.UUID;

public class MemberId extends BaseUUIDId {
    private MemberId(UUID value) {
        super(value);
    }

    private MemberId() {
        super();
    }

    public static MemberId withId(UUID uuid) {
        return new MemberId(uuid);
    }
}
