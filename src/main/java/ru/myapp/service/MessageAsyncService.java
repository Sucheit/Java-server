package ru.myapp.service;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.myapp.error.NotFoundException;
import ru.myapp.persistence.model.Message;
import ru.myapp.persistence.repository.MessageRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageAsyncService {

  private final TransactionTemplate transactionTemplate;
  private final MessageRepository messageRepository;

  @Async("taskExecutor")
  public void processInnerTransaction(Integer id) {
    transactionTemplate.executeWithoutResult(status -> {
      try {
        Message message = messageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Message not found by id=%s".formatted(id)));
        message.setMessage(message.getMessage().substring(0, 36) + ": " + OffsetDateTime.now());
        messageRepository.save(message);
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
              @Override
              public void afterCommit() {
                log.info("Inner transaction committed on thread: {}",
                    Thread.currentThread().threadId());
              }
            }
        );
      } catch (Exception e) {
        log.error("Error happened in inner transaction");
        status.setRollbackOnly();
      }
    });
  }
}
