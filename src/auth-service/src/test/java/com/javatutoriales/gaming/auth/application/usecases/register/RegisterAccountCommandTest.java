package com.javatutoriales.gaming.auth.application.usecases.register;

import org.junit.jupiter.api.Test;


class RegisterAccountCommandTest {
    @Test
    void testBuilder(){
        RegisterAccountCommand command = RegisterAccountCommand.builder()
                .member()
                .email("email")
                .buildMember()
                .credentials()
                .username("user")
                .password("pass")
                .buildCredentials()
                .build();

        System.out.println(command);
    }

}