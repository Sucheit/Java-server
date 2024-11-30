package ru.myapp.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.kafka.consumer.MessageListener;
import ru.myapp.service.UserService;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserListenerImpl implements MessageListener<UserRequestDto> {

    private final UserService userService;
    private final ScheduledExecutorService executor;

    @Override
    @KafkaListener(topics = "#{kafkaProps.topics.users}",
            containerFactory = "kafkaListenerContainerFactory",
            properties = {"spring.json.value.default.type=ru.myapp.dto.request.UserRequestDto"})
    public void listenMessage(@Payload UserRequestDto userRequestDto) {
        log.info("Kafka received: {}", userRequestDto);

        executor.schedule(() -> savingUser(userRequestDto), 5, TimeUnit.SECONDS);
        executor.shutdown();
    }

    private void savingUser(UserRequestDto userRequestDto) {
        log.info("Saving user: {}", userRequestDto);
        userService.createEntity(userRequestDto);
    }
}
