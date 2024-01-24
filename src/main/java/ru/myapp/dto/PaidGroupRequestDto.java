package ru.myapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record PaidGroupRequestDto(

        @NotBlank
        @Size(min = 6, max = 100)
        String name,

        @NotBlank
        @Size(min = 10, max = 255)
        String description,

        @NotNull
        @Range(min = 10, max = 1000)
        Integer cost) {
}
