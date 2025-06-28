package ru.myapp.kafka.consumer.impl;

import jakarta.validation.Valid;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.service.ItemService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemListenerImpl {

  private final ItemService itemService;
  private final ScheduledExecutorService itemsScheduledExecutorService;

  @KafkaListener(topics = "#{kafkaProps.topics.items}",
      containerFactory = "kafkaListenerContainerFactory",
      errorHandler = "validationErrorHandler",
      properties = {"spring.json.value.default.type=ru.myapp.dto.request.ItemRequestDto"})
  @SendTo("#{kafkaProps.topics.DLT}")
  public void listenMessage(@Payload @Valid ItemRequestDto itemRequestDto) {
    log.info("Kafka received: {}", itemRequestDto);
    itemsScheduledExecutorService.schedule(() -> saveItem(itemRequestDto), 5, TimeUnit.SECONDS);
  }

  private void saveItem(ItemRequestDto itemRequestDto) {
    log.info("Saving item: {}", itemRequestDto);
    itemService.saveItem(itemRequestDto);
  }
}
