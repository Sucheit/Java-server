package ru.myapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "User model request")
public record UserRequestDto(

        @NotBlank
        @Size(min = 2, max = 255)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 255)
        String lastName) {
}
