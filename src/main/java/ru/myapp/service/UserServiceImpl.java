package ru.myapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.dto.UserRequestDto;
import ru.myapp.dto.UserResponseDto;
import ru.myapp.dto.UserResponseDtoShort;
import ru.myapp.error.BadRequestException;
import ru.myapp.error.NotFoundException;
import ru.myapp.mappers.UserMapper;
import ru.myapp.model.Group;
import ru.myapp.model.User;
import ru.myapp.repository.GroupRepository;
import ru.myapp.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final GroupRepository groupRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.groupRepository = groupRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDtoShort> getAllEntities() {
        return userMapper.userListToUserResponseDtoShortList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto getEntityById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
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
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        user.setFirstName(userRequestDto.firstName());
        user.setLastName(userRequestDto.lastName());
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public void deleteEntityById(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseDto addUserToGroup(Integer userId, Integer groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        Set<Group> groups = user.getGroups();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        groups.add(group);
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto deleteUserFromGroup(Integer userId, Integer groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        Set<Group> groups = user.getGroups();
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        if (!groups.contains(group)) {
            throw new BadRequestException((String.format("User id=%s is not in the group id=%s", userId, groupId)));
        }
        groups.remove(group);
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }
}
