package com.javatutoriales.gaming.users.application.usecases.register;

import com.javatutoriales.gaming.users.commons.domain.valueobjects.Member;
import com.javatutoriales.gaming.users.commons.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.commons.domain.valueobjects.Profile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterAccountCommandTest {
    @Test
    void givenAValidRegisterAccountCommandTest_whenTheMemberIsRetrieved_thenTheMemberContainsTheSameOriginalParameters() {
        
        var memberFirstName = "firstName";
        var memberLastName = "lastName";
        var memberEmail = "user@email.com";

        RegisterAccountCommand command = RegisterAccountCommand.builder()
                .member(Member.builder().firstName(memberFirstName).lastName(memberLastName).email(memberEmail).build())
                .credentials(Credentials.builder().username("username").password("password").build())
                .profile(Profile.PARTICIPANT)
                .build();
        
        assertThat(command.member()).isNotNull();
        
        Member member = command.member();

        assertThat(member.getFirstName()).isEqualTo(memberFirstName);
        assertThat(member.getLastName()).isEqualTo(memberLastName);
        assertThat(member.getEmail()).isEqualTo(memberEmail);

    }
}