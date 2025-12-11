package ru.myapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record PassportRequestDto(

        @NotEmpty
        @Pattern(regexp = "[0-9]{5}")
        String serialNumber
) {
}
