package ru.myapp.dto.response;

import java.time.Instant;

public record ItemResponseDto(

        Integer id,

        String name,

        String description,

        Integer amount,

        Instant createdAt,

        Instant updatedAt,

        String createdBy,

        String updatedBy) {
}
