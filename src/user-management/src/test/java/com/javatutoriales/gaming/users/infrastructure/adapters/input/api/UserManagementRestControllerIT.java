package com.javatutoriales.gaming.users.infrastructure.adapters.input.api;

import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.RegisterAccountRequest;
import com.javatutoriales.gaming.users.infrastructure.adapters.output.events.AccountRegisteredEvent;
import com.javatutoriales.gaming.users.infrastructure.adapters.output.messaging.KafkaStorageOutputPort;
import com.javatutoriales.gaming.users.infrastructure.model.Account;
import com.javatutoriales.gaming.users.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("testcontainers")
class UserManagementRestControllerIT {

    private final String HOST = "http://localhost";

    @Autowired
    private UserManagementRestController restController;

    @Autowired
    private EntityManager entityManager;

    @MockBean
    private KafkaStorageOutputPort kafkaOutputPort;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(restController);
    }

    @Test
    void givenAValidCreateAccountRequest_whenTheAccountIsCreated_thenItShouldHaveAnAccountIdAndDatesAndAnAccountRegisteredEventIsSend() throws UnsupportedEncodingException {
        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest("user", "password", "firstName", "lastName", "email@email.com", Profile.STAFF);

        final String apiPath = "/v1/users";
        final String expectedLocation = "(%s%s/)%s".formatted(HOST, apiPath, TestUtils.UUID_PATTERN);

        MockMvcResponse response = given()
                .contentType(ContentType.JSON)
                .body(registerAccountRequest)
                .when()
                .post(apiPath, registerAccountRequest)
                .then()
                .assertThat()
                .body("value", matchesRegex(TestUtils.UUID_PATTERN))
                .contentType(ContentType.JSON)
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, matchesRegex(expectedLocation))
                .extract().response();

        var responseBody = response.getMvcResult().getResponse().getContentAsString();

        Pattern accountIdPattern = Pattern.compile(TestUtils.UUID_PATTERN);

        Matcher accountIdMatcher = accountIdPattern.matcher(responseBody);

        if (!accountIdMatcher.find()) {
            fail("AccountId was not fond in the response body. Found %s".formatted(responseBody));
        }

        var accountId = accountIdMatcher.group(0);

        Account account = entityManager.find(Account.class, UUID.fromString(accountId));

        assertThat(account).isNotNull();
        assertThat(account.getVersion()).isZero();
        assertThat(account.getCreatedDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(account.getCreatedDate()).isEqualTo(account.getModifiedDate());

        verify(kafkaOutputPort, times(1)).registerAccount(any(AccountRegisteredEvent.class));
        verifyNoMoreInteractions(kafkaOutputPort);
    }

}
