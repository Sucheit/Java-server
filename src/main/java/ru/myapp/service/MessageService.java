package ru.myapp.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.myapp.dto.request.BatchMessage;

public interface MessageService {

    void saveMessage(ConsumerRecord<String, BatchMessage> consumerRecord);

    void sendMessages(int amountOfMessages);
}
