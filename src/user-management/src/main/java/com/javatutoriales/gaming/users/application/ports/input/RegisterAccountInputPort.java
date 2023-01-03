package com.javatutoriales.gaming.users.application.ports.input;

import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountCommand;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountUseCase;
import com.javatutoriales.gaming.users.domain.entities.Account;
import com.javatutoriales.gaming.users.domain.entities.Member;
import com.javatutoriales.gaming.users.domain.events.AccountCreatedEvent;
import com.javatutoriales.gaming.users.domain.exceptions.UsernameDuplicatedException;
import com.javatutoriales.gaming.users.domain.specifications.UsernameUniqueSpecification;
import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.shared.validations.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public class RegisterAccountInputPort implements RegisterAccountUseCase {

    private final AccountStorageOutputPort membersRegistryOutputPort;

    @Override
    public AccountCreatedEvent registerAccount(RegisterAccountCommand registerAccountCommand) throws UsernameDuplicatedException {

        validateInputData(registerAccountCommand);

        Account newAccount = Account.builder()
                .accountId(AccountId.withRandomId())
                .credentials(registerAccountCommand.credentials())
                .member(registerAccountCommand.member())
                .profile(registerAccountCommand.profile())
                .build();

        return new AccountCreatedEvent(newAccount);
    }

    private void validateInputData(RegisterAccountCommand registerAccountCommand) {
        Validator.validateBean(registerAccountCommand);

        Optional<Member> maybeMember = membersRegistryOutputPort.findMemberByUsername(registerAccountCommand.member().getEmail());

        UsernameUniqueSpecification usernameUniqueSpecification = new UsernameUniqueSpecification(maybeMember);

        usernameUniqueSpecification.check(registerAccountCommand.member());
        // TODO: Verify password's complexity and that the current user can create new users with the specified profile
    }
}
