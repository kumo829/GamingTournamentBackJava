package com.javatutoriales.gaming.users.domain.specifications;

import com.javatutoriales.gaming.users.domain.exceptions.InsecurePasswordException;
import com.javatutoriales.gaming.users.domain.valueobjects.Credentials;
import com.javatutoriales.shared.domain.exception.SpecificationException;
import com.javatutoriales.shared.domain.specification.Specification;
import org.passay.*;

import java.util.List;

public class PasswordComplexitySpecification implements Specification<Credentials> {
    private static final PasswordValidator passwordValidator;

    static {
        passwordValidator = new PasswordValidator(
                // length between 8 and 30 characters
                new LengthRule(8, 30),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // define some illegal sequences that will fail when >= 5 chars long
                // alphabetical is of the form 'abcde', numerical is '34567', qwery is 'asdfg'
                // the false parameter indicates that wrapped sequences are allowed; e.g. 'xyzabc'
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),

                // no whitespace
                new WhitespaceRule(),

                // no contain the username
                new UsernameRule()
        );
    }


    @Override
    public boolean isSatisfiedBy(Credentials credentials) {
        RuleResult validate = validateCredentials(credentials);

        return validate.isValid();
    }

    @Override
    public void check(Credentials credentials) throws SpecificationException {
        RuleResult ruleResult = validateCredentials(credentials);

        List<RuleResultDetail> resultList = ruleResult.getDetails();

        if(!resultList.isEmpty()){
            var errorMessages = passwordValidator.getMessages(ruleResult);

            var message = "Password is not secure enough. %s".formatted(errorMessages);

            throw new InsecurePasswordException(message, errorMessages.toArray(String[]::new));
        }
    }

    private RuleResult validateCredentials(Credentials credentials) {
        PasswordData data = new PasswordData(credentials.getUsername(), credentials.getPassword());

        return passwordValidator.validate(data);
    }
}
