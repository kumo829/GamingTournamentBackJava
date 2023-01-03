package com.javatutoriales.gaming.users.domain.entities;

import com.javatutoriales.gaming.users.domain.valueobjects.MemberId;
import com.javatutoriales.shared.domain.entity.BaseEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Data
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity<MemberId> {
    @NotEmpty
    private final String firstName;
    @NotEmpty
    private final String lastName;
    @NotEmpty
    @Email
    private final String email;

    @Builder
    public Member(MemberId memberId, String firstName, String lastName, String email) {
        super(memberId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
