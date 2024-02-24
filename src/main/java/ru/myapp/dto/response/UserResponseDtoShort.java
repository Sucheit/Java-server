package ru.myapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "Short user model response")
public record UserResponseDtoShort(Integer id,
                                   String firstName,
                                   String lastName) {

    @Builder
    public UserResponseDtoShort {
    }
}
