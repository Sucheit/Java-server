package ru.myapp.mappers;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.myapp.config.MapstructConfig;
import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.utils.GoogleTimestampConverter;

@Mapper(config = MapstructConfig.class)
public interface GrpcMapper {

  @Mapping(target = "offsetDateTime",
      expression = "java(toOffsetDateTime(dto))")
  ItemRequestDto map(ru.arthur.inzhilov.grpc.stubs.ItemRequestDto dto);

  default OffsetDateTime toOffsetDateTime(ru.arthur.inzhilov.grpc.stubs.ItemRequestDto dto) {
    Timestamp eventTime = dto.getEventTime();
    String timezoneOffset = dto.getTimezoneOffset();
    Instant instant = GoogleTimestampConverter.timestampToInstant(eventTime);
    ZoneOffset zoneOffset = ZoneOffset.of(timezoneOffset);
    return OffsetDateTime.ofInstant(instant, zoneOffset);
  }
}
