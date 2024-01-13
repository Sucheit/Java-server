package ru.myapp.service;

import ru.myapp.dto.UserRequestDto;
import ru.myapp.dto.UserResponseDto;
import ru.myapp.dto.UserResponseDtoShort;

import java.util.List;

public interface UserService {

    List<UserResponseDtoShort> getUsers();

    UserResponseDto getUserById(Integer userId);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(Integer userId, UserRequestDto userRequestDto);

    void deleteUser(Integer userId);

    UserResponseDto addUserToGroup(Integer userId, Integer groupId);

    UserResponseDto deleteUserFromGroup(Integer userId, Integer groupId);
}
