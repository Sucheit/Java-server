package ru.myapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.aspect.AfterReturningAnnotation;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.dto.response.UserResponseDto;
import ru.myapp.dto.response.UserResponseDtoShort;
import ru.myapp.error.BadRequestException;
import ru.myapp.error.NotFoundException;
import ru.myapp.mappers.UserMapper;
import ru.myapp.persistence.model.Group;
import ru.myapp.persistence.model.User;
import ru.myapp.persistence.repository.GroupRepository;
import ru.myapp.persistence.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final GroupRepository groupRepository;

    @Override
    @AfterReturningAnnotation
    @Transactional(readOnly = true)
    public List<UserResponseDtoShort> getAllEntities(PageRequest pageRequest) {
        return userMapper.userListToUserResponseDtoShortList(userRepository.findAll(pageRequest).stream().toList());
    }

    @Override
    @AfterReturningAnnotation
    @Transactional(readOnly = true)
    public List<UserResponseDtoShort> getAllEntities() {
        return userMapper.userListToUserResponseDtoShortList(userRepository.getAllUsers());
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
        User user = userRepository.getUserById(userId)
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
        User user = userRepository.getUserById(userId)
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

    private static ExampleMatcher getExampleMatcher() {
        return ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatcher::exact);
    }

    @Override
    public List<UserResponseDto> getAllUsersByExample(UserRequestDto userRequestDto, PageRequest pageRequest) {
        Example<User> userExample = Example.of(userMapper.mapToEntity(userRequestDto), getExampleMatcher());
        return userRepository.findAll(userExample, pageRequest).stream()
                .map(userMapper::userToUserResponseDto)
                .toList();
    }
}
