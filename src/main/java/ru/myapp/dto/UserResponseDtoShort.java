package ru.myapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Short user model response")
public record UserResponseDtoShort(Integer id,
                                   String firstName,
                                   String lastName) {
}
