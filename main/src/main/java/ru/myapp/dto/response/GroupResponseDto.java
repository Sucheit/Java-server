package ru.myapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(name = "Group model response")
public record GroupResponseDto(Integer id,
                               String name,
                               String description,
                               Set<UserResponseDtoShort> users) {

}
