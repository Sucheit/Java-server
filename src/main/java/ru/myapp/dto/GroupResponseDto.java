package ru.myapp.dto;

import java.util.Set;

public record GroupResponseDto(Integer id,
                               String name,
                               String description,
                               Set<UserResponseDtoShort> users) {
}
