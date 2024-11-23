package ru.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.config.kafka.KafkaProps;
import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.dto.response.ItemResponseDto;
import ru.myapp.error.NotFoundException;
import ru.myapp.kafka.publisher.MessagePublisher;
import ru.myapp.mappers.ItemMapper;
import ru.myapp.persistence.model.Item;
import ru.myapp.persistence.repository.ItemRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final MessagePublisher messagePublisher;
    private final KafkaProps kafkaProperties;

    @Override
    public void sendToKafka(ItemRequestDto itemRequestDto) {
        Item item = itemMapper.toItem(itemRequestDto);
        log.info("Sent to kafka: {}", item);
        messagePublisher.publish(kafkaProperties.getTopics().getItems(), item);
    }

    @Override
    @Transactional
    public void saveItem(Item item) {
        Item itemSaved = itemRepository.save(item);
        log.info("Saved: {}", itemSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponseDto getItemById(Integer itemId) {
        return itemMapper.toItemResponseDto(itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item id=%s not found".formatted(itemId))));
    }
}
