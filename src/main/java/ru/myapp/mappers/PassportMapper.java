package ru.myapp.mappers;

import org.mapstruct.Mapper;
import ru.myapp.config.MapstructConfig;
import ru.myapp.dto.response.PassportResponseDto;
import ru.myapp.persistence.model.Passport;


@Mapper(config = MapstructConfig.class, uses = {UserMapper.class})
public interface PassportMapper {

    PassportResponseDto mapToPassportResponseDto(Passport passport);
}
