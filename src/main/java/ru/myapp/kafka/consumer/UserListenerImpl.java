package ru.myapp.kafka.consumer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.myapp.dto.response.UserResponseDtoShort;
import ru.myapp.service.ItemService;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserListenerImpl implements MessageListener<UserResponseDtoShort> {

    private final ItemService itemService;

    @Override
    @KafkaListener(topics = "${kafka.topics.users-topic}", groupId = "${spring.kafka.consumer.group-id.users-group-id}")
    public void listenMessage(@Valid UserResponseDtoShort userResponseDtoShort) {
        log.info("Kafka received: {}", userResponseDtoShort);
    }
}
