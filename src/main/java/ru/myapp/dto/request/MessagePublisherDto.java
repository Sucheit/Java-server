package ru.myapp.dto.request;

import java.util.Map;
import lombok.Builder;

@Builder
public record MessagePublisherDto(
    Integer partition,
    String key,
    Object message,
    Map<String, String> headers) {

}
