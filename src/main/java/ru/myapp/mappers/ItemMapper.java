package ru.myapp.mappers;

import org.mapstruct.Mapper;
import ru.myapp.config.MapstructConfig;
import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.dto.response.ItemResponseDto;
import ru.myapp.persistence.model.Item;

@Mapper(config = MapstructConfig.class)
public interface ItemMapper {

    ItemResponseDto toItemResponseDto(Item item);

    Item toItem(ItemRequestDto itemRequestDto);
}
