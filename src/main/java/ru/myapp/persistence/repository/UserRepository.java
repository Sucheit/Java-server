package ru.myapp.persistence.repository;

import jakarta.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import ru.myapp.persistence.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @EntityGraph(attributePaths = "groups")
  @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
  Optional<User> getUserById(Integer userId);

  @Query("SELECT u FROM User u ")
  @EntityGraph(attributePaths = "passport")
  @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
  List<User> getAllUsers();
}
