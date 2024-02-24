package ru.myapp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Group model request")
public record GroupRequestDto(

        @NotBlank
        @Size(min = 6, max = 100)
        String name,

        @NotBlank
        @Size(min = 10, max = 255)
        String description) {
}
