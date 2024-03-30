package ru.myapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myapp.model.Passport;

import java.util.Optional;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Integer> {

    @EntityGraph(attributePaths = "user")
    Optional<Passport> getPassportsByUserId(Integer userId);

    @EntityGraph(attributePaths = "user")
    Optional<Passport> getPassportsById(Integer passportId);
}
