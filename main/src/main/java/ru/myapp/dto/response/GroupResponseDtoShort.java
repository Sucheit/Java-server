package ru.myapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Short group model response")
public record GroupResponseDtoShort(Integer id,
                                    String name,
                                    String description) {
}