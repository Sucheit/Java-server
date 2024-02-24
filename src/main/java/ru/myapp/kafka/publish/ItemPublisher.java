package ru.myapp.kafka.publish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.myapp.model.Item;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemPublisher implements MessagePublisher<Item> {

    private final KafkaTemplate<String, Item> kafkaTemplate;
    @Value("${kafka.topics.items-topic}")
    private String topicName;

    @Override
    public void publish(Item item) {
        kafkaTemplate
                .send(topicName, item)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Sent message with {} to topic [{}] with offset=[{}]", item, topicName, result.getRecordMetadata().offset());
                    } else {
                        log.error("Unable to send message to topic [{}] due to : {}", topicName, ex.getMessage());
                    }
                });
    }
}
