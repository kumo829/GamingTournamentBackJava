package com.javatutoriales.gaming.users.application.ports.input;

import com.javatutoriales.gaming.users.application.ports.output.MembersRegistryOutputPort;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountCommand;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountUseCase;
import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.exceptions.UsernameDuplicatedException;
import com.javatutoriales.gaming.users.domain.specifications.UsernameUniqueSpecification;
import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;


@RequiredArgsConstructor
public class RegisterAccountService implements RegisterAccountUseCase {

    private final MembersRegistryOutputPort membersRegistryOutputPort;

    @Override
    public AccountId registerAccount(RegisterAccountCommand registerAccountCommand) {

        Stream<Member> membersStream = membersRegistryOutputPort.getAll();

        UsernameUniqueSpecification usernameUniqueSpecification = new UsernameUniqueSpecification(membersStream);

        if(!usernameUniqueSpecification.isSatisfiedBy(registerAccountCommand.member())){
            throw new UsernameDuplicatedException("There is already an account with that username", "UDE-001");
        }

        return null;
    }
}
