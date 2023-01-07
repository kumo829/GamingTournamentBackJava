package com.javatutoriales.gaming.users.application.usecases.register;

import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.domain.valueobjects.MemberId;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterAccountCommandTest {
    @Test
    void givenAValidRegisterAccountCommandTest_whenTheMemberIsRetrieved_thenTheMemberContainsTheSameOriginalParameters() {
        
        var memberId = MemberId.withId(UUID.randomUUID());
        var memberFirstName = "firstName";
        var memberLastName = "lastName";
        var memberEmail = "user@email.com";

        RegisterAccountCommand command = RegisterAccountCommand.builder()
                .member(Member.builder().memberId(memberId).firstName(memberFirstName).lastName(memberLastName).email(memberEmail).build())
                .credentials(Credentials.builder().username("username").password("password").build())
                .profile(Profile.PARTICIPANT)
                .build();
        
        assertThat(command.member()).isNotNull();
        
        Member member = command.member();

        assertThat(member.getId()).isEqualTo(memberId);
        assertThat(member.getFirstName()).isEqualTo(memberFirstName);
        assertThat(member.getLastName()).isEqualTo(memberLastName);
        assertThat(member.getEmail()).isEqualTo(memberEmail);

    }
}