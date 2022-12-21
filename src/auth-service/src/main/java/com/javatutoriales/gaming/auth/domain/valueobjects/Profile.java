package com.javatutoriales.gaming.auth.domain.valueobjects;

import lombok.*;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Profile {
    private final String name;

    public static Profile admin(){
        return new Profile("admin");
    }

    public static Profile participant(){
        return new Profile("participant");
    }

    public static Profile staff() {
        return new Profile("staff");
    }
}
