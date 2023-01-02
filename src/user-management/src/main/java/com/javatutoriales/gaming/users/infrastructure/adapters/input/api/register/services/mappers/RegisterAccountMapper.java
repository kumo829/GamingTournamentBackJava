package com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services.mappers;

import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountCommand;
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
}
