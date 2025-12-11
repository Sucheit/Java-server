package ru.myapp.dto.response;

public record PassportResponseDto(

        Integer id,

        UserResponseDtoShort user,

        String serialNumber
) {
}
