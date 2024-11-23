package ru.myapp.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.myapp.dto.response.UserResponseDtoShort;
import ru.myapp.kafka.consumer.MessageListener;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserListenerImpl implements MessageListener<UserResponseDtoShort> {

    @Override
    @KafkaListener(topics = "#{kafkaProps.topics.users}",
            containerFactory = "kafkaListenerContainerFactory",
            properties = {"spring.json.value.default.type=ru.myapp.dto.response.UserResponseDtoShort"})
    public void listenMessage(@Payload UserResponseDtoShort userResponseDtoShort) {
        log.info("Kafka received: {}", userResponseDtoShort);
    }
}
