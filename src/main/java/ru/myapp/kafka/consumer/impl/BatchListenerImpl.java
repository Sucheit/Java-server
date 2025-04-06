package ru.myapp.kafka.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.myapp.dto.request.BatchMessage;
import ru.myapp.kafka.consumer.BatchMessageListener;
import ru.myapp.service.MessageService;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchListenerImpl implements BatchMessageListener<ConsumerRecord<String, BatchMessage>> {

    @Qualifier("taskExecutor")
    private final ExecutorService executor;
    private final MessageService messageService;

    @Override
    @KafkaListener(topics = "#{kafkaProps.topics.batchMessages}",
            containerFactory = "kafkaListenerContainerFactory",
            batch = "true",
            properties = {"spring.json.value.default.type=ru.myapp.dto.request.BatchMessage"})
    public void listenBatchMessages(@Payload List<ConsumerRecord<String, BatchMessage>> messages) throws InterruptedException {
        log.info("Kafka received batch size = {}", messages.size());
        CountDownLatch countDownLatch = new CountDownLatch(messages.size());
        messages.forEach(message -> executor.submit(() -> {
            System.out.println(message);
            messageService.saveMessage(message);
            countDownLatch.countDown();
        }));
        countDownLatch.await();
        log.info("Kafka batch executed!");
    }
}
