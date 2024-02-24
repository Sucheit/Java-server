package ru.myapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.aspect.AfterReturningAnnotation;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.dto.response.UserResponseDto;
import ru.myapp.dto.response.UserResponseDtoShort;
import ru.myapp.error.BadRequestException;
import ru.myapp.error.NotFoundException;
import ru.myapp.kafka.publish.MessagePublisher;
import ru.myapp.mappers.UserMapper;
import ru.myapp.model.Group;
import ru.myapp.model.User;
import ru.myapp.repository.GroupRepository;
import ru.myapp.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final GroupRepository groupRepository;

    private final MessagePublisher<UserResponseDtoShort> userResponsePublisher;

    @Transactional(readOnly = true)
    @Override
    @AfterReturningAnnotation
    public List<UserResponseDtoShort> getAllEntities() {
        return userMapper.userListToUserResponseDtoShortList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto getEntityById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id=%s not found".formatted(userId)));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto createEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.firstName());
        user.setLastName(userRequestDto.lastName());
        user = userRepository.save(user);
        userResponsePublisher.publish(userMapper.userToUserResponseDtoShort(user));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateEntity(Integer userId, UserRequestDto userRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id=%s not found".formatted(userId)));
        user.setFirstName(userRequestDto.firstName());
        user.setLastName(userRequestDto.lastName());
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public void deleteEntityById(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id=%s not found".formatted(userId)));
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseDto addUserToGroup(Integer userId, Integer groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id=%s not found".formatted(userId)));
        Set<Group> groups = user.getGroups();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group id=%s not found".formatted(groupId)));
        groups.add(group);
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto deleteUserFromGroup(Integer userId, Integer groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id=%s not found".formatted(userId)));
        Set<Group> groups = user.getGroups();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group id=%s not found".formatted(groupId)));
        if (!groups.contains(group)) {
            throw new BadRequestException(("User id=%s is not in the group id=%s".formatted(userId, groupId)));
        }
        groups.remove(group);
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }
}
