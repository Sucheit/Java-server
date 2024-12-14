package ru.myapp.persistence.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.persistence.model.QPredicates;
import ru.myapp.persistence.model.User;
import ru.myapp.persistence.repository.UserFilterRepository;

import java.util.List;

import static ru.myapp.persistence.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserFilterRepositoryImpl implements UserFilterRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<User> getAllUsersByQueryDSL(UserRequestDto userRequestDto) {
        Predicate predicate = QPredicates.builder()
                .add(userRequestDto.firstName(), user.firstName::eq)
                .add(userRequestDto.lastName(), user.lastName::containsIgnoreCase)
                .build();
        return new JPAQuery<User>(em).select(user).from(user).where(predicate).fetch();
    }
}
