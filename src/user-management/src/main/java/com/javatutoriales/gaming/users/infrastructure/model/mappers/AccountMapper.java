package com.javatutoriales.gaming.users.infrastructure.model.mappers;

import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "member.firstName", source = "firstName")
    @Mapping(target = "member.lastName", source = "lastName")
    @Mapping(target = "member.email", source = "email")
    @Mapping(target = "credentials.username", source = "username")
    @Mapping(target = "credentials.password", source = "password")
    @Mapping(target = "accountId", source = "id")
    Account infraToDomain(com.javatutoriales.gaming.users.infrastructure.model.Account infraAccount);

    @Mapping(target = "firstName", source = "member.firstName")
    @Mapping(target = "lastName", source = "member.lastName")
    @Mapping(target = "email", source = "member.email")
    @Mapping(target = "username", source = "credentials.username")
    @Mapping(target = "password", source = "credentials.password")
    @Mapping(target = "id", source = "id")
    com.javatutoriales.gaming.users.infrastructure.model.Account domainToInfra(Account domainAccount);

    default String accountIdToUUID(AccountId accountId) {
        return accountId.getValue().toString();
    }
    default AccountId uuidToAccountId(UUID uuid) {
        return AccountId.withId(uuid);
    }

    default AccountId stringToAccountId(String uuid) {
        return AccountId.withId(uuid);
    }

    default UUID  uuidToAccountId(AccountId accountId) {
        return accountId.getValue();
    }
}
