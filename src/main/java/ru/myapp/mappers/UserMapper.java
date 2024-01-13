package ru.myapp.mappers;

import org.mapstruct.Mapper;
import ru.myapp.dto.UserResponseDto;
import ru.myapp.dto.UserResponseDtoShort;
import ru.myapp.model.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = GroupMapper.class)
public interface UserMapper {

    UserResponseDto userToUserResponseDto(User user);

    UserResponseDtoShort userToUserResponseDtoShort(User user);

    List<UserResponseDtoShort> userListToUserResponseDtoShortList(List<User> userList);
}
