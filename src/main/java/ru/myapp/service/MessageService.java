package ru.myapp.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.domain.PageRequest;
import ru.myapp.dto.request.BatchMessage;
import ru.myapp.dto.response.MessageResponseDto;

import java.util.List;

public interface MessageService {

    void saveMessage(ConsumerRecord<String, BatchMessage> consumerRecord);

    void sendMessages(int amountOfMessages);

    List<MessageResponseDto> getMessages(PageRequest pageRequest);

    MessageResponseDto getMessageByMessageId(String messageId);

}
