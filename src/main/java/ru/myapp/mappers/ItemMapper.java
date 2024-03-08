package ru.myapp.mappers;

import org.mapstruct.Mapper;
import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.dto.response.ItemResponseDto;
import ru.myapp.model.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemResponseDto toItemResponseDto(Item item);

    Item toItem(ItemRequestDto itemRequestDto);
}
