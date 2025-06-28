package ru.myapp.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.config.kafka.KafkaProps;
import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.dto.response.ItemResponseDto;
import ru.myapp.error.NotFoundException;
import ru.myapp.kafka.publisher.MessagePublisher;
import ru.myapp.mappers.ItemMapper;
import ru.myapp.persistence.model.Item;
import ru.myapp.persistence.model.ItemFullInfo;
import ru.myapp.persistence.model.ItemProjection;
import ru.myapp.persistence.model.ItemView;
import ru.myapp.persistence.repository.ItemRepository;
import ru.myapp.service.ItemService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;
  private final ItemMapper itemMapper;
  @Qualifier("TransactionalMessagePublisherImpl")
  private final MessagePublisher messagePublisher;
  private final KafkaProps kafkaProperties;
  @Lazy
  private final ItemServiceImpl itemServiceImpl;

  @Override
  @Transactional(transactionManager = "kafkaTransactionManager")
  public void sendToKafka(ItemRequestDto itemRequestDto) {
    log.info("Sent to kafka: {}", itemRequestDto);
    messagePublisher.publish(kafkaProperties.getTopics().getItems(), itemRequestDto);
  }

  @Override
  @Transactional
  public void saveItem(ItemRequestDto itemRequestDto) {
    Item itemSaved = itemRepository.save(itemMapper.toItem(itemRequestDto));
    itemServiceImpl.getItemById(itemSaved.getId());
    log.info("Saved: {}", itemSaved);
  }

  @Override
  @Cacheable(cacheNames = "items", key = "#itemId")
  @Transactional(readOnly = true)
  public ItemResponseDto getItemById(Integer itemId) {
    return itemMapper.toItemResponseDto(itemRepository.findById(itemId)
        .orElseThrow(() -> new NotFoundException("Item id=%s not found".formatted(itemId))));
  }

  @Override
  @Transactional
  @CacheEvict(cacheNames = {"items"})
  public void deleteItem(Integer itemId) {
    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new NotFoundException("Item id=%s not found".formatted(itemId)));
    itemRepository.delete(item);
  }

  @Override
  @CachePut(cacheNames = "items", key = "#itemId")
  public ItemResponseDto putItem(ItemRequestDto itemRequestDto, Integer itemId) {
    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new NotFoundException("Item id=%s not found".formatted(itemId)));
    Optional.of(itemRequestDto.name()).ifPresent(item::setName);
    Optional.of(itemRequestDto.description()).ifPresent(item::setDescription);
    Optional.of(itemRequestDto.amount()).ifPresent(item::setAmount);
    return itemMapper.toItemResponseDto(itemRepository.save(item));
  }

  @Override
  public ItemView getItemView(Integer itemId) {
    return itemRepository.findById(itemId, ItemView.class);
  }

  @Override
  public ItemFullInfo getFullInfo(Integer itemId) {
    return itemRepository.findById(itemId, ItemFullInfo.class);
  }

  @Override
  public ItemProjection getItemProjection(Integer itemId) {
    return itemRepository.findById(itemId, ItemProjection.class);
  }
}
