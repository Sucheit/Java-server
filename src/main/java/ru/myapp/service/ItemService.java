package ru.myapp.service;

import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.dto.response.ItemResponseDto;
import ru.myapp.persistence.model.Item;

public interface ItemService {

    void sendToKafka(ItemRequestDto itemRequestDto);

    void saveItem(Item item);

    ItemResponseDto getItemById(Integer itemId);

    void deleteItem(Integer itemId);

    ItemResponseDto putItem(ItemRequestDto itemRequestDto, Integer itemId);
}
