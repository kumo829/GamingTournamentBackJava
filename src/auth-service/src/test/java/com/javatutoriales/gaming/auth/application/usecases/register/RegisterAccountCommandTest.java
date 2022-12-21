package com.javatutoriales.gaming.auth.application.usecases.register;

import com.javatutoriales.gaming.auth.domain.entities.Member;
import com.javatutoriales.gaming.auth.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.auth.domain.valueobjects.Profile;
import org.junit.jupiter.api.Test;


class RegisterAccountCommandTest {
    @Test
    void testBuilder() {
        RegisterAccountCommand command = RegisterAccountCommand.builder()
                .member(Member.builder().email("email").build())
                .credentials(Credentials.builder().username("username").password("password").build())
                .profile(Profile.participant())
                .build();

        System.out.println(command);
    }

}