package ru.myapp.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.myapp.dto.request.BatchMessage;
import ru.myapp.service.MessageService;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchListenerImpl {

    @Qualifier("taskExecutor")
    private final ExecutorService executor;
    private final MessageService messageService;

    @KafkaListener(topics = "#{kafkaProps.topics.batchMessages}",
            containerFactory = "kafkaListenerContainerFactory",
            batch = "true",
            properties = {"spring.json.value.default.type=ru.myapp.dto.request.BatchMessage"})
    public void listenBatchMessages(@Payload List<ConsumerRecord<String, BatchMessage>> messages) {
        log.info("Kafka received batch size = {}", messages.size());
        var countDownLatch = new CountDownLatch(messages.size());
        messages.forEach(message -> executor.submit(() -> {
            System.out.println(message);
            messageService.saveMessage(message);
            countDownLatch.countDown();
        }));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("Ошибка при обработки сообщений списком: {}", e.getMessage());
        }

        log.info("Kafka batch executed!");
    }
}
