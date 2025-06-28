package ru.myapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.config.kafka.KafkaProps;
import ru.myapp.dto.request.BatchMessage;
import ru.myapp.dto.request.MessagePublisherDto;
import ru.myapp.dto.request.MessageRequestDto;
import ru.myapp.dto.response.MessageResponseDto;
import ru.myapp.error.NotFoundException;
import ru.myapp.kafka.publisher.BatchMessagePublisher;
import ru.myapp.mappers.MessageMapper;
import ru.myapp.persistence.model.Message;
import ru.myapp.persistence.repository.MessageRepository;
import ru.myapp.service.MessageService;
import ru.myapp.utils.Constants;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final MessageMapper messageMapper;
  @Qualifier("batchMessagePublisherImpl")
  private final BatchMessagePublisher messagePublisher;
  private final KafkaProps kafkaProps;

  @Override
  @Transactional
  public void saveMessage(ConsumerRecord<String, BatchMessage> consumerRecord) {
    messageRepository
        .save(messageMapper
            .toEntity(new String(consumerRecord.headers().lastHeader("messageId").value()),
                consumerRecord.value()));
    log.info("Consumer record is saved in Database!");
  }

  @Override
  @Transactional(transactionManager = "kafkaTransactionManager")
  public void sendMessages(int amountOfMessaged) {
    List<MessagePublisherDto> messageDtoList = new ArrayList<>(Constants.BATCH_SIZE);
    IntStream.range(0, amountOfMessaged)
        .forEach(num -> messageDtoList.add(MessagePublisherDto.builder()
            .message(MessageRequestDto.builder().message(UUID.randomUUID().toString()).build())
            .headers(Map.of("messageId", Integer.valueOf(num).toString()))
            .build()));
    messagePublisher.publishBatch(kafkaProps.getTopics().getBatchMessages(), messageDtoList);
    log.info("Producer records are formed and sent for publishing!");
  }

  @Override
  public List<MessageResponseDto> getMessages(PageRequest pageRequest) {
    return messageMapper.toResponseDtos(messageRepository.findAll(pageRequest).get().toList());
  }

  @Override
  @Transactional(timeout = 10)
  public MessageResponseDto getMessageByMessageId(String messageId) {
    Message message = messageRepository.findByMessageId(messageId)
        .orElseThrow(
            () -> new NotFoundException(
                "Message not found with messageId=%s".formatted(messageId)));
    return messageMapper.toResponseDto(message);
  }
}
