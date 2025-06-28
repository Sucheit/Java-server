package ru.myapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(name = "User model response")
public record UserResponseDto(Integer id,
                              String firstName,
                              String lastName,
                              Set<GroupResponseDtoShort> groups) {

}
