package com.javatutoriales.gaming.auth.domain.entities;

import com.javatutoriales.gaming.auth.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.auth.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.auth.domain.valueobjects.Profile;
import com.javatutoriales.shared.domain.entity.AggregateRoot;
import lombok.Builder;

@Builder
public class Account extends AggregateRoot<AccountId> {
    private Credentials credentials;
    private Member member;
    private Profile profile;
}
