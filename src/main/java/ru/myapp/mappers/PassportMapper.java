package ru.myapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.myapp.dto.response.PassportResponseDto;
import ru.myapp.model.Passport;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {UserMapper.class})
public interface PassportMapper {

    PassportResponseDto mapToPassportResponseDto(Passport passport);
}
