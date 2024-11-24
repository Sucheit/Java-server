package ru.myapp.kafka.publisher.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.myapp.kafka.publisher.MessagePublisher;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisherImpl implements MessagePublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String topic, Integer partition, String key, Object message, Map<String, String> headers) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, partition, key, message, mapToHeaders(headers));
        kafkaTemplate
                .send(record)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Sent message with {} to topic [{}] with offset=[{}]", message, topic, result.getRecordMetadata().offset());
                    } else {
                        log.error("Unable to send message to topic [{}] due to : {}", topic, ex.getMessage());
                    }
                });
    }

    private List<Header> mapToHeaders(Map<String, String> headers) {
        return headers.entrySet().stream()
                .map(e -> (Header) new RecordHeader(e.getKey(), e.getValue().getBytes(StandardCharsets.UTF_8)))
                .toList();
    }
}
