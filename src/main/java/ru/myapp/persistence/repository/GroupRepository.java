package ru.myapp.persistence.repository;

import jakarta.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.myapp.persistence.model.Group;
import ru.myapp.persistence.model.PaidGroup;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {


  @Query("SELECT pg FROM PaidGroup pg")
  @EntityGraph(attributePaths = "users")
  @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
  List<PaidGroup> findAllPaidGroups();

  @Query("SELECT g FROM Group g")
  @EntityGraph(attributePaths = "users")
  @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
  List<Group> findAllGroups();

  @EntityGraph(attributePaths = "users")
  @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
  @Query("select pg from PaidGroup pg where pg.id = ?1")
  Optional<PaidGroup> findPaidGroupById(@NonNull Integer paidGroupId);
}
