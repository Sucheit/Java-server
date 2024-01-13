package ru.myapp.dto;

import java.util.Set;

public record UserResponseDto(Integer id,
                              String firstName,
                              String lastName,
                              Set<GroupResponseDtoShort> groups) {
}
