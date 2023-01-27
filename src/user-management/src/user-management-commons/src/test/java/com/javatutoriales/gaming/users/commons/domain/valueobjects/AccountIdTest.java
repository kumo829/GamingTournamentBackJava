package com.javatutoriales.gaming.users.commons.domain.valueobjects;

import com.javatutoriales.gaming.users.commons.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountIdTest {

    @Nested
    @DisplayName("withId(String)")
    class WithIdString {
        @Test
        @DisplayName("Value is equal to provided String")
        void givenAnAccountIdWithAProvidedId_whenReadingTheValueOfTheAccountId_thenTheIdShouldBeEqualToTheProvidedOne() {

            var providedID = UUID.randomUUID().toString();

            AccountId accountId = AccountId.withId(providedID);

            assertThat(accountId).isNotNull();
            assertThat(accountId.getValue()).isNotNull();
            assertThat(accountId.getValue()).hasToString(providedID);
        }

        @Test
        @DisplayName("toString contains the provided String")
        void givenAnAccountIdWithAProvidedId_whenInvokeToString_thenTheIdShouldContainProvidedId() {
            var providedID = UUID.randomUUID().toString();

            AccountId accountId = AccountId.withId(providedID);

            assertThat(accountId).hasToString("AccountId=%s".formatted(providedID));
        }
    }
    @Nested
    @DisplayName("withId(UUID)")
    class WithIdUUID {
        @Test
        @DisplayName("Value is equal to provided UUID")
        void givenAnAccountIdWithAProvidedId_whenReadingTheValueOfTheAccountId_thenTheIdShouldBeEqualToTheProvidedOne() {

            var providedID = UUID.randomUUID();

            AccountId accountId = AccountId.withId(providedID);

            assertThat(accountId).isNotNull();
            assertThat(accountId.getValue()).isNotNull();
            assertThat(accountId.getValue()).isEqualTo(providedID);
        }

        @Test
        @DisplayName("toString contains the provided UUID")
        void givenAnAccountIdWithAProvidedId_whenInvokeToString_thenTheIdShouldContainProvidedId() {
            var providedID = UUID.randomUUID();

            AccountId accountId = AccountId.withId(providedID);

            assertThat(accountId).hasToString("AccountId=%s".formatted(providedID));
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
            assertThat(accountId.getValue().toString()).matches(TestUtils.UUID_PATTERN);
        }

        @Test
        @DisplayName("toString contains a valid UUID")
        void givenAnAccountIdWithARandomId_whenInvokeToString_thenItShouldBeAValidUUID() {
            AccountId accountId = AccountId.withRandomId();

            assertThat(accountId.toString()).matches("^AccountId=" + TestUtils.UUID_PATTERN);
        }
    }
}