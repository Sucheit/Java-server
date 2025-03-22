package ru.myapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.myapp.dto.request.BatchMessage;
import ru.myapp.persistence.model.MessageEntity;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "message", source = "message")
    MessageEntity toEntity(BatchMessage batchMessage);
}
