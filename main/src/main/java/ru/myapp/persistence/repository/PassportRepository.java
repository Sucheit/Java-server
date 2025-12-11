package ru.myapp.persistence.repository;

import jakarta.persistence.QueryHint;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import ru.myapp.persistence.model.Passport;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Integer> {

  @EntityGraph(attributePaths = "user")
  @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
  Optional<Passport> getPassportsByUserId(Integer userId);

  @EntityGraph(attributePaths = "user")
  @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
  Optional<Passport> getPassportsById(Integer passportId);
}
