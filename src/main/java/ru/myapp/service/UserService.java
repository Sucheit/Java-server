package ru.myapp.service;

import ru.myapp.dto.UserRequestDto;
import ru.myapp.dto.UserResponseDto;
import ru.myapp.dto.UserResponseDtoShort;

import java.util.List;

public interface UserService extends CrudService<Integer, UserRequestDto, UserResponseDto, UserResponseDtoShort> {

    List<UserResponseDtoShort> getAllEntities();

    UserResponseDto getEntityById(Integer userId);

    UserResponseDto createEntity(UserRequestDto userRequestDto);

    UserResponseDto updateEntity(Integer userId, UserRequestDto userRequestDto);

    void deleteEntityById(Integer userId);

    UserResponseDto addUserToGroup(Integer userId, Integer groupId);

    UserResponseDto deleteUserFromGroup(Integer userId, Integer groupId);
}
