package ru.myapp.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.myapp.kafka.consumer.MessageListener;
import ru.myapp.persistence.model.Item;
import ru.myapp.service.ItemService;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemListenerImpl implements MessageListener<Item> {

    private final ItemService itemService;
    private final ScheduledExecutorService executor;

    @Override
    @KafkaListener(topics = "#{kafkaProps.topics.items}",
            containerFactory = "kafkaListenerContainerFactory",
            properties = {"spring.json.value.default.type=ru.myapp.persistence.model.Item"})
    public void listenMessage(@Payload Item item) {
        log.info("Kafka received: {}", item);
        executor.schedule(() -> saveItem(item), 5, TimeUnit.SECONDS);
        executor.shutdown();
    }

    private void saveItem(Item item) {
        log.info("Saving item: {}", item);
        itemService.saveItem(item);
    }
}
