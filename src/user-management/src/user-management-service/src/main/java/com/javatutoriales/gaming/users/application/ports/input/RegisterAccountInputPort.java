package com.javatutoriales.gaming.users.application.ports.input;

import com.javatutoriales.gaming.users.application.ports.output.AccountStorageOutputPort;
import com.javatutoriales.gaming.users.application.ports.output.PasswordEncoderOutputPort;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountCommand;
import com.javatutoriales.gaming.users.application.usecases.register.RegisterAccountUseCase;
import com.javatutoriales.gaming.users.commons.domain.entities.Account;
import com.javatutoriales.gaming.users.commons.domain.events.AccountCreatedEvent;
import com.javatutoriales.gaming.users.domain.exceptions.UsernameDuplicatedException;
import com.javatutoriales.gaming.users.domain.specifications.PasswordComplexitySpecification;
import com.javatutoriales.gaming.users.domain.specifications.UsernameUniqueSpecification;
import com.javatutoriales.gaming.users.commons.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.commons.domain.valueobjects.Credentials;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services.mappers.RegisterAccountMapper;
import com.javatutoriales.shared.validations.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.UnaryOperator;


@RequiredArgsConstructor
public class RegisterAccountInputPort implements RegisterAccountUseCase {

    private final AccountStorageOutputPort membersRegistryOutputPort;
    private final PasswordEncoderOutputPort passwordEncoder;
    private final RegisterAccountMapper accountMapper;

    @Override
    public AccountCreatedEvent registerAccount(RegisterAccountCommand registerAccountCommand) throws UsernameDuplicatedException {

        validateInputData(registerAccountCommand);

        Account newAccount = Account.builder()
                .accountId(AccountId.withRandomId())
                .credentials(registerAccountCommand.credentials())
                .credentialCustomizer(encodeCredentials())
                .member(registerAccountCommand.member())
                .profile(registerAccountCommand.profile())
                .build();

        return new AccountCreatedEvent(newAccount);
    }

    private void validateInputData(RegisterAccountCommand registerAccountCommand) {
        Validator.validateBean(registerAccountCommand);

        Optional<Account> maybeMember = membersRegistryOutputPort.findByUsername(registerAccountCommand.member().getEmail());

        Account newAccount = accountMapper.commandToDomain(registerAccountCommand);

        new UsernameUniqueSpecification(maybeMember).check(newAccount.getMember());
        new PasswordComplexitySpecification().check(newAccount.getCredentials());
    }

    private UnaryOperator<Credentials> encodeCredentials(){
        return c -> Credentials.builder()
                .username(c.getUsername())
                .password(c.getPassword())
                .passwordCustomizer(password -> encodePassword(c.getPassword()))
                .build();
    }

    private String encodePassword(String originalPassword) {
        return passwordEncoder != null ? passwordEncoder.encode(originalPassword) : originalPassword;
    }
}
