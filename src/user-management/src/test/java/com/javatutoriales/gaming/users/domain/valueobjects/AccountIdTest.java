package com.javatutoriales.gaming.users.domain.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountIdTest {

    @Nested
    @DisplayName("withId")
    class WithId {
        @Test
        @DisplayName("Value is equal to provided UUID")
        void givenAnAccountIdWithAProvidedId_whenReadingTheValueOfTheAccountId_thenTheIdShouldBeEqualToTheProvidedOne() {

            var providedID = UUID.randomUUID().toString();

            AccountId accountId = AccountId.withId(providedID);

            assertThat(accountId).isNotNull();
            assertThat(accountId.getValue()).isNotNull();
            assertThat(accountId.getValue().toString()).isEqualTo(providedID);
        }

        @Test
        @DisplayName("toString contains the provided UUID")
        void givenAnAccountIdWithAProvidedId_whenInvokeToString_thenTheIdShouldContainProvidedId() {
            var providedID = UUID.randomUUID().toString();

            AccountId accountId = AccountId.withId(providedID);

            assertThat(accountId.toString()).isEqualTo("AccountId=%s".formatted(providedID));
        }
    }

    @Nested
    @DisplayName("withRandomId")
    class WithRandomId {
        @Test
        @DisplayName("Valid UUID is generated randomly")
        void givenAnAccountIdWithARandomId_whenReadingTheValueOfTheAccountId_thenItShouldBeAValidUUID() {
            AccountId accountId = AccountId.withRandomId();

            assertThat(accountId).isNotNull();
            assertThat(accountId.getValue()).isNotNull();
            assertThat(accountId.getValue().toString()).matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$");
        }

        @Test
        @DisplayName("toString contains a valid UUID")
        void givenAnAccountIdWithARandomId_whenInvokeToString_thenItShouldBeAValidUUID() {
            AccountId accountId = AccountId.withRandomId();

            assertThat(accountId.toString()).matches("^AccountId=[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$");
        }
    }
}