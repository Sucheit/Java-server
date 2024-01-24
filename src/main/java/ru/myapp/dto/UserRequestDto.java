package ru.myapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserRequestDto(

        @NotBlank
        @Size(min = 2, max = 255)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 255)
        String lastName) {
}
