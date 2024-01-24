package ru.myapp.dto;

import java.util.Set;

public record PaidGroupResponseDto(Integer id,
                                   String name,
                                   String description,
                                   Integer cost,
                                   Set<UserResponseDtoShort> users) {
}