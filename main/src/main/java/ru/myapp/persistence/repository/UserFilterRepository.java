package ru.myapp.persistence.repository;

import java.util.List;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.persistence.model.User;

public interface UserFilterRepository {

  List<User> getAllUsersByQueryDSL(UserRequestDto userRequestDto);
}
