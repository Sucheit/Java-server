package ru.myapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.dto.response.UserResponseDtoShort;
import ru.myapp.mappers.UserMapper;
import ru.myapp.model.User;
import ru.myapp.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Captor
    ArgumentCaptor<User> captor;
    @Spy
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetAllUser_expectOk() {
        List<User> userList = List.of(new User(7, "User1", "User1LastName"),
                new User(19, "user2", "user2LastName"));
        when(userRepository.findAll()).thenReturn(userList);

        List<UserResponseDtoShort> users = userService.getAllEntities();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(7, users.get(0).id());
        assertEquals("User1", users.get(0).firstName());
        assertEquals("User1LastName", users.get(0).lastName());
        assertEquals(19, users.get(1).id());
        assertEquals("user2", users.get(1).firstName());
        assertEquals("user2LastName", users.get(1).lastName());

        verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    public void testCreateUser_givenValidDate_expectOk() {
        UserRequestDto userRequestDto = new UserRequestDto("name1", "lastName1");
        userService.createEntity(userRequestDto);

        verify(userRepository).save(captor.capture());
        User userSaved = captor.getValue();

        assertNotNull(userSaved);
        assertEquals("name1", userSaved.getFirstName());
        assertEquals("lastName1", userSaved.getLastName());
        verify(userRepository, atLeast(1)).save(any(User.class));
    }
}