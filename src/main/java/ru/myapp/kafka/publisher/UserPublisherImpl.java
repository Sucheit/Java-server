package ru.myapp.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.myapp.dto.response.UserResponseDtoShort;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPublisherImpl implements MessagePublisher<UserResponseDtoShort> {

    private final KafkaTemplate<String, UserResponseDtoShort> kafkaTemplate;
    @Value("${kafka.topics.users-topic}")
    private String topicName;

    @Override
    public void publish(UserResponseDtoShort userResponseDtoShort) {
        kafkaTemplate
                .send(topicName, userResponseDtoShort)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Sent message to topic [{}] with offset=[{}]", topicName, result.getRecordMetadata().offset());
                    } else {
                        log.error("Unable to send message to topic [{}] due to : {}", topicName, ex.getMessage());
                    }
                });
    }
}
