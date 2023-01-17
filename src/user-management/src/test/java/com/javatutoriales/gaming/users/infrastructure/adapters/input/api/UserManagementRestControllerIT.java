package com.javatutoriales.gaming.users.infrastructure.adapters.input.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.handlers.RestExceptionHandler;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.RegisterAccountRequest;
import com.javatutoriales.gaming.users.infrastructure.adapters.output.events.AccountRegisteredEvent;
import com.javatutoriales.gaming.users.infrastructure.adapters.output.messaging.KafkaStorageOutputPort;
import com.javatutoriales.gaming.users.infrastructure.adapters.output.messaging.Topics;
import com.javatutoriales.gaming.users.infrastructure.model.Account;
import com.javatutoriales.gaming.users.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.path.json.JsonPath;
import jakarta.persistence.EntityManager;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.kafka.test.assertj.KafkaConditions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainers")
class UserManagementRestControllerIT {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.1"));

    @Autowired
    private UserManagementRestController restController;

    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @Autowired
    private EntityManager entityManager;

    static ConsumerFactory<UUID, String> consumerFactory;
    private Consumer<UUID, String> consumer;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @BeforeAll
    static void setUpTest() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(kafkaContainer.getBootstrapServers(), "testGroup", "true");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.UUIDDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
    }

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(restController, restExceptionHandler);
        consumer = consumerFactory.createConsumer();
    }

    @Test
    @DisplayName("POST /v1/users - CREATED")
    void givenAValidCreateAccountRequest_whenTheAccountIsCreated_thenItShouldHaveAnAccountIdAndDatesAndAnAccountRegisteredEventIsSend() throws UnsupportedEncodingException, JsonProcessingException {
        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest("user", "h5PaT4To@kB!G&XM", "firstName", "lastName", "email@email.com", Profile.STAFF);

        final String apiPath = "/v1/users";
        final String expectedLocation = "(%s%s/)%s".formatted(TestUtils.HOST, apiPath, TestUtils.UUID_PATTERN);

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

        consumer.subscribe(List.of(Topics.ACCOUNT_REGISTERED));

        ConsumerRecords<UUID, String> messagesConsumed = consumer.poll( Duration.of(3, ChronoUnit.SECONDS));
        assertThat(messagesConsumed).isNotNull();
        assertThat(messagesConsumed.count()).isEqualTo(1);

        ConsumerRecord<UUID, String> message = messagesConsumed.iterator().next();

        assertThat(message).has(key(UUID.fromString(accountId)))
                .has(partition(0));
    }


    @Test
    @DisplayName("POST /v1/users - ERROR")
    void givenAnAccountDtoWithoutARequiredField_whenSendingARequestToRegisterTheAccount_thenAnErrorWithTheListOfMissingFieldsShouldBeReceived() throws UnsupportedEncodingException {
        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest(null, "password", "", "lastName", "email@email.com", Profile.STAFF);

        MockMvcResponse response = given()
                .contentType(ContentType.JSON)
                .body(registerAccountRequest)
                .when()
                .post("/v1/users", registerAccountRequest)
                .then()
                .assertThat()
                .status(HttpStatus.BAD_REQUEST).extract().response();


        var mvcResult = response.getMvcResult();
        var contentType = mvcResult.getResponse().getContentType();
        var contentBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonPath jsonPath = JsonPath.from(contentBody);

        assertThat(contentType).isEqualTo(APPLICATION_PROBLEM_JSON.toString());
        assertThat(jsonPath.getString("title")).isEqualTo("Bad Request");
        assertThat(jsonPath.getString("detail")).isEqualTo("The provided parameters are invalid");
        assertThat(jsonPath.getInt("fields.size()")).isEqualTo(2);

        var errorsList = jsonPath.getMap("fields");
        assertThat(errorsList)
                .containsEntry("firstName", "must not be blank")
                .containsEntry("username", "must not be blank")
                .doesNotContainKey("password")
                .doesNotContainKey("lastName")
                .doesNotContainKey("email")
                .doesNotContainKey("profile");


        var resolvedException = mvcResult.getResolvedException();
        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);

        String message = resolvedException.getMessage();

        assertThat(message)
                .contains("with 2 errors")
                .contains("firstName")
                .contains("username")
                .contains("must not be blank")
                .doesNotContain("password")
                .doesNotContain("lastName")
                .doesNotContain("email")
                .doesNotContain("profile");
    }
}
