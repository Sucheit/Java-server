package ru.myapp.mappers;

import org.mapstruct.Mapper;
import ru.myapp.config.MapstructConfig;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.dto.response.UserResponseDto;
import ru.myapp.dto.response.UserResponseDtoShort;
import ru.myapp.persistence.model.User;

import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {

    UserResponseDto userToUserResponseDto(User user);

    UserResponseDtoShort userToUserResponseDtoShort(User user);

    List<UserResponseDtoShort> userListToUserResponseDtoShortList(List<User> userList);

    User mapToEntity(UserRequestDto userRequestDto);
}
