package ru.myapp.kafka.listen;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.model.Item;
import ru.myapp.service.ItemService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ItemListenerImpl implements MessageListener<Item> {

    private final ItemService itemService;

    @Override
    @KafkaListener(topics = "${kafka.topics.items-topic}", groupId = "${spring.kafka.consumer.group-id.users-group-id}")
    public void listenMessage(@Valid Item item) {
        log.info("Kafka received: {}", item);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> saveItem(item), 5, TimeUnit.SECONDS);
        executor.shutdown();
    }

    private void saveItem(Item item) {
        log.info("Saving item: {}", item);
        itemService.saveItem(item);
    }
}
