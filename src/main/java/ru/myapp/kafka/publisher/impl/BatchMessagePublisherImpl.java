package ru.myapp.kafka.publisher.impl;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.myapp.dto.request.MessagePublisherDto;
import ru.myapp.kafka.publisher.BatchMessagePublisher;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchMessagePublisherImpl implements BatchMessagePublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  public void publishBatch(String topic, List<MessagePublisherDto> messageDtoList) {
    List<ProducerRecord<String, Object>> producerRecordList = new ArrayList<>();
    messageDtoList.forEach(message -> producerRecordList.add(
        new ProducerRecord<>(topic, message.partition(), message.key(), message.message(),
            mapToHeaders(message.headers()))
    ));

    producerRecordList
        .forEach(producerRecord -> {
          kafkaTemplate
              .send(producerRecord)
              .whenComplete((result, ex) -> {
                if (ex == null) {
                  log.info("Sent message with {} to topic [{}] with offset=[{}]",
                      producerRecord.value(), topic, result.getRecordMetadata().offset());
                } else {
                  log.error("Unable to send message to topic [{}] due to : {}", topic,
                      ex.getMessage());
                }
              });
        });
  }
}
