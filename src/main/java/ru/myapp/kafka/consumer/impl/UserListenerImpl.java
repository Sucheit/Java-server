package ru.myapp.kafka.consumer.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.service.UserService;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserListenerImpl {

    private final UserService userService;
    private final ScheduledExecutorService usersScheduledExecutorService;

    @KafkaListener(topics = "#{kafkaProps.topics.users}",
            containerFactory = "kafkaListenerContainerFactory",
            errorHandler = "validationErrorHandler",
            properties = {"spring.json.value.default.type=ru.myapp.dto.request.UserRequestDto"})
    @SendTo("#{kafkaProps.topics.DLT}")
    public void listenMessage(@Payload @Valid UserRequestDto userRequestDto) {
        log.info("Kafka received: {}", userRequestDto);

        usersScheduledExecutorService.schedule(() -> savingUser(userRequestDto), 5, TimeUnit.SECONDS);
    }

    private void savingUser(UserRequestDto userRequestDto) {
        log.info("Saving user: {}", userRequestDto);
        userService.createEntity(userRequestDto);
    }
}
