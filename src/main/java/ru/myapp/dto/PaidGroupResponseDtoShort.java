package ru.myapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Short paid group response")
public record PaidGroupResponseDtoShort(Integer id,

                                        String name,

                                        String description,

                                        Integer cost) {
}
