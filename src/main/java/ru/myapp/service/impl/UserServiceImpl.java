package ru.myapp.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.aspect.AfterReturningAnnotation;
import ru.myapp.config.kafka.KafkaProps;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.dto.response.UserResponseDto;
import ru.myapp.dto.response.UserResponseDtoShort;
import ru.myapp.error.BadRequestException;
import ru.myapp.error.NotFoundException;
import ru.myapp.kafka.publisher.MessagePublisher;
import ru.myapp.mappers.UserMapper;
import ru.myapp.persistence.model.Group;
import ru.myapp.persistence.model.User;
import ru.myapp.persistence.repository.GroupRepository;
import ru.myapp.persistence.repository.UserFilterRepository;
import ru.myapp.persistence.repository.UserRepository;
import ru.myapp.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final GroupRepository groupRepository;
    private final MessagePublisher messagePublisher;
    private final KafkaProps kafkaProps;
    private final UserFilterRepository userFilterRepository;
    @PersistenceContext
    private final EntityManager entityManager;

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
        User user = User.builder()
                .firstName(userRequestDto.firstName())
                .lastName(userRequestDto.lastName())
                .build();
        user = userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public void sendToKafka(UserRequestDto userRequestDto) {
        log.info("Sending {} to kafka!", userRequestDto);
        messagePublisher.publish(kafkaProps.getTopics().getUsers(), userRequestDto);
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
    public List<UserResponseDtoShort> getAllUsersByExample(UserRequestDto userRequestDto, PageRequest pageRequest) {
        Example<User> userExample = Example.of(userMapper.mapToEntity(userRequestDto), getExampleMatcher());
        return userMapper.userListToUserResponseDtoShortList(
                userRepository.findAll(userExample, pageRequest).stream().toList());
    }

    @Override
    public List<UserResponseDtoShort> getAllUsersByQueryDSL(UserRequestDto userRequestDto) {
        return userMapper.userListToUserResponseDtoShortList(
                userFilterRepository.getAllUsersByQueryDSL(userRequestDto));
    }

    @Override
    public List<UserResponseDtoShort> getAllUsersByCriteriaAPI(UserRequestDto userRequestDto) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        String firstName = userRequestDto.firstName();
        String lastName = userRequestDto.lastName();

        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(builder.like(root.get("firstName"), firstName));
        }
        if (lastName != null) {
            predicates.add(builder.like(root.get("lastName"), "%" + lastName + "%"));
        }
        if (!predicates.isEmpty()) {
            criteria.where(builder.and(predicates.toArray(new Predicate[0])));
        }
        return entityManager.createQuery(criteria).getResultList().stream()
                .map(userMapper::userToUserResponseDtoShort)
                .toList();
    }
}
