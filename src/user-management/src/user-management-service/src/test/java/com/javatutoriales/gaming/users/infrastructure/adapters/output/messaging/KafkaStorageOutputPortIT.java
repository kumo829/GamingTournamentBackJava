package com.javatutoriales.gaming.users.infrastructure.adapters.output.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.gaming.users.infrastructure.adapters.output.events.AccountRegisteredEvent;
import com.javatutoriales.gaming.users.infrastructure.model.Account;
import com.javatutoriales.gaming.users.utils.TestUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class KafkaStorageOutputPortIT {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.1"));

    private static final Duration TIMEOUT_DURATION = Duration.of(3, ChronoUnit.SECONDS);

    @Autowired
    private KafkaStorageOutputPort kafkaOutputPort;

    @Autowired
    private ObjectMapper objectMapper;

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
        consumer = consumerFactory.createConsumer();
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
    }
}
