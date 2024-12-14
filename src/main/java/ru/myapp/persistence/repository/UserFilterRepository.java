package ru.myapp.persistence.repository;

import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.persistence.model.User;

import java.util.List;

public interface UserFilterRepository {

    List<User> getAllUsersByQueryDSL(UserRequestDto userRequestDto);
}
