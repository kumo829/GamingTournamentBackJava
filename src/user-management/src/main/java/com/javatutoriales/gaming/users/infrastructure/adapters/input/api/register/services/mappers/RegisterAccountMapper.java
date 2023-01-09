package com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services.mappers;

import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountCommand;
import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.RegisterAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RegisterAccountMapper {
    @Mapping(target = "member.firstName", source = "firstName")
    @Mapping(target = "member.lastName", source = "lastName")
    @Mapping(target = "member.email", source = "email")
    @Mapping(target = "credentials.username", source = "username")
    @Mapping(target = "credentials.password", source = "password")
    RegisterAccountCommand requestToCommand(RegisterAccountRequest request);


    @Mapping(target = "accountId",constant = "00000000-0000-0000-0000-000000000000")
    Account commandToDomain(RegisterAccountCommand command);

    default AccountId stringToAccountId(String accountId) {
        return AccountId.withId(accountId);
    }

}
