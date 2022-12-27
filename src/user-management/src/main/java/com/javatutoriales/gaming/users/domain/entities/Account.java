package com.javatutoriales.gaming.users.domain.entities;

import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.shared.domain.entity.AggregateRoot;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
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
}
