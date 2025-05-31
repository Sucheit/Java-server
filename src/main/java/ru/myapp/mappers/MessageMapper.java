package ru.myapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.myapp.config.MapstructConfig;
import ru.myapp.dto.request.BatchMessage;
import ru.myapp.dto.response.MessageResponseDto;
import ru.myapp.persistence.model.Message;

import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface MessageMapper {

    @Mapping(target = "messageId", source = "header")
    @Mapping(target = "message", source = "batchMessage.message")
    Message toEntity(String header, BatchMessage batchMessage);

    MessageResponseDto toResponseDto(Message messageEntity);

    List<MessageResponseDto> toResponseDtos(List<Message> messageEntities);
}
