package ru.myapp.service;

import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.dto.response.ItemResponseDto;
import ru.myapp.persistence.model.ItemFullInfo;
import ru.myapp.persistence.model.ItemProjection;
import ru.myapp.persistence.model.ItemView;

public interface ItemService {

  void sendToKafka(ItemRequestDto itemRequestDto);

  void saveItem(ItemRequestDto itemRequestDto);

  ItemResponseDto getItemById(Integer itemId);

  void deleteItem(Integer itemId);

  ItemResponseDto putItem(ItemRequestDto itemRequestDto, Integer itemId);

  ItemView getItemView(Integer itemId);

  ItemFullInfo getFullInfo(Integer itemId);

  ItemProjection getItemProjection(Integer itemId);
}
