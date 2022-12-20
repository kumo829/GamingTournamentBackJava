package com.javatutoriales.gaming.auth.domain.entities;

import com.javatutoriales.gaming.auth.domain.valueobjects.AccountId;
import com.javatutoriales.shared.domain.entity.AggregateRoot;

public class Account extends AggregateRoot<AccountId> {
    private AccountId accountId;
    private Member member;
    private Profile profile;
}
