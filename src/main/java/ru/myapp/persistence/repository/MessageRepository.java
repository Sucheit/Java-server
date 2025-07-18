package ru.myapp.persistence.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import ru.myapp.persistence.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

  @Lock(value = LockModeType.PESSIMISTIC_WRITE)
  Optional<Message> findByMessageId(String messageId);
}
