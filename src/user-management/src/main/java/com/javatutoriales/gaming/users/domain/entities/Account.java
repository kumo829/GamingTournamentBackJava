package com.javatutoriales.gaming.users.domain.entities;

import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.shared.domain.entity.AggregateRoot;
import com.javatutoriales.shared.validations.Validator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Account extends AggregateRoot<AccountId> {
    @NotNull
    @Valid
    private Credentials credentials;
    @NotNull
    @Valid
    private Member member;
    @NotNull
    @Valid
    private Profile profile;

    @Builder
    private Account(AccountId accountId, Credentials credentials, Member member, Profile profile) {
        super(accountId);
        this.credentials = credentials;
        this.member = member;
        this.profile = profile;

        Validator.validateBean(this);
    }
}
