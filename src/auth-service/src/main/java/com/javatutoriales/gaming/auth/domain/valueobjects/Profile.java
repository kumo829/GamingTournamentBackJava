package com.javatutoriales.gaming.auth.domain.valueobjects;

import lombok.*;

@RequiredArgsConstructor
@Getter
public enum Profile {
    ADMIN("Administrator"),
    PARTICIPANT("Participant"),
    STAFF("Staff");
    private final String name;
}
