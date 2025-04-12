package ru.myapp.dto.request;

import lombok.Builder;

import java.util.Map;

@Builder
public record MessagePublisherDto(
        Integer partition,
        String key,
        Object message,
        Map<String, String> headers) {
}
