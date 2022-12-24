package com.javatutoriales.gaming.users.domain.entities;

import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.shared.domain.entity.AggregateRoot;
import lombok.Builder;

@Builder
public class Account extends AggregateRoot<AccountId> {
    private Credentials credentials;
    private Member member;
    private Profile profile;
}
