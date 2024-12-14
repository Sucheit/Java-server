package ru.myapp.service;

import org.springframework.data.domain.PageRequest;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.dto.response.UserResponseDto;
import ru.myapp.dto.response.UserResponseDtoShort;

import java.util.List;

public interface UserService extends CrudService<Integer, UserRequestDto, UserResponseDto, UserResponseDtoShort> {

    List<UserResponseDtoShort> getAllEntities(PageRequest pageRequest);

    List<UserResponseDtoShort> getAllEntities();

    UserResponseDto getEntityById(Integer userId);

    UserResponseDto createEntity(UserRequestDto userRequestDto);

    void sendToKafka(UserRequestDto userRequestDto);

    UserResponseDto updateEntity(Integer userId, UserRequestDto userRequestDto);

    void deleteEntityById(Integer userId);

    UserResponseDto addUserToGroup(Integer userId, Integer groupId);

    UserResponseDto deleteUserFromGroup(Integer userId, Integer groupId);

    List<UserResponseDtoShort> getAllUsersByExample(UserRequestDto userRequestDto, PageRequest pageRequest);

    List<UserResponseDtoShort> getAllUsersByQueryDSL(UserRequestDto userRequestDto);
}
