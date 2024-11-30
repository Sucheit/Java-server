package ru.myapp.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.myapp.config.kafka.KafkaProps;
import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.kafka.publisher.MessagePublisher;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "activity", name = "kafka-sender", havingValue = "true")
public class ScheduledKafkaSender implements ScheduledService {

    private final MessagePublisher messagePublisher;
    private final KafkaProps kafkaProps;

    private static ItemRequestDto getItemRequestDto() {
        return ItemRequestDto.builder()
                .name("Item name")
                .description("Item description")
                .amount(10)
                .build();
    }

    @Override
    @Async("taskExecutor")
    @Scheduled(fixedRate = 5000)
    public void scheduled() {
        messagePublisher.publish(kafkaProps.getTopics().getItems(), getItemRequestDto());
    }
}
