package com.javatutoriales.gaming.users.infrastructure.adapters.output.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.gaming.users.infrastructure.adapters.output.events.AccountRegisteredEvent;
import com.javatutoriales.gaming.users.infrastructure.model.Account;
import com.javatutoriales.gaming.users.utils.TestUtils;
import io.restassured.path.json.JsonPath;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:29092", "port=29092" })
class KafkaStorageOutputPortTest {

    private static final Duration TIMEOUT_DURATION = Duration.of(3, ChronoUnit.SECONDS);

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private KafkaStorageOutputPort kafkaOutputPort;

    @Autowired
    private ObjectMapper objectMapper;

    private Consumer<UUID, String> consumer;

    @BeforeEach
    void setUp(){
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", this.embeddedKafka);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.UUIDDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        ConsumerFactory<UUID, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
    }

    @Test
    void givenAnAccountRegisteredEvent_whenTheAccountIsRegistered_thenAMessageIsSentToTheAccountRegisteredEventTopic() throws Exception {
        var accountId = UUID.randomUUID();
        var firstName = "FirstName";
        var lastName = "LastName";
        var email = "user@mail.com";
        var username = "username";
        var password = TestUtils.SECURE_PASSWORD;

        AccountRegisteredEvent event = new AccountRegisteredEvent(new Account(accountId, firstName, lastName, email, username, password, Profile.ADMIN));
        String jsonEvent = objectMapper.writeValueAsString(event);

        kafkaOutputPort.registerAccount(event);

        consumer.subscribe(List.of(Topics.ACCOUNT_REGISTERED));

        ConsumerRecords<UUID, String> messagesConsumed = consumer.poll(TIMEOUT_DURATION);
        Assertions.assertThat(messagesConsumed).isNotNull();
        Assertions.assertThat(messagesConsumed.count()).isEqualTo(1);

        ConsumerRecord<UUID, String> message = messagesConsumed.iterator().next();

        Assertions.assertThat(message).has(KafkaConditions.key(accountId))
                .has(KafkaConditions.value(jsonEvent))
                .has(KafkaConditions.partition(0));

        JsonPath jsonPath = new JsonPath(message.value());

        Assertions.assertThat(jsonPath.getUUID("account.id")).isEqualTo(accountId);
        Assertions.assertThat(jsonPath.getString("account.firstName")).isEqualTo(firstName);
        Assertions.assertThat(jsonPath.getString("account.lastName")).isEqualTo(lastName);
        Assertions.assertThat(jsonPath.getString("account.email")).isEqualTo(email);
        Assertions.assertThat(jsonPath.getString("account.username")).isEqualTo(username);
        Assertions.assertThat(jsonPath.getString("account.password")).isEqualTo(password);
        Assertions.assertThat(jsonPath.getString("account.profile")).isEqualTo(Profile.ADMIN.toString());
    }

}