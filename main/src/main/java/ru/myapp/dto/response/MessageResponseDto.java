package ru.myapp.dto.response;

public record MessageResponseDto(
        Integer id,
        String messageId,
        String message) {
}
