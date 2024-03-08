package ru.myapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ItemRequestDto(

        @NotBlank
        @Size(min = 3, max = 255)
        String name,

        @NotBlank
        @Size(min = 5, max = 255)
        String description,

        @Positive
        @NotNull
        Integer amount) {
}
