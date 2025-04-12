package ru.myapp.kafka.publisher.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.myapp.kafka.publisher.MessagePublisher;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service(value = "TransactionalMessagePublisherImpl")
public class TransactionalMessagePublisherImpl implements MessagePublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String topic, Integer partition, String key, Object message, Map<String, String> headers) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, partition, key, message, mapToHeaders(headers));
        kafkaTemplate
                .executeInTransaction(t -> t.send(record)
                        .whenComplete((result, ex) -> {
                            if (ex == null) {
                                log.info("Sent message with {} to topic [{}] with offset=[{}]", message, topic, result.getRecordMetadata().offset());
                            } else {
                                log.error("Unable to send message to topic [{}] due to : {}", topic, ex.getMessage());
                            }
                        }));

    }
}
