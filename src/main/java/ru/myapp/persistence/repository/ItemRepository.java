package ru.myapp.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myapp.persistence.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
