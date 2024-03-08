package ru.myapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(name = "Paid group model response")
public record PaidGroupResponseDto(Integer id,
                                   String name,
                                   String description,
                                   Integer cost,
                                   Set<UserResponseDtoShort> users) {
}