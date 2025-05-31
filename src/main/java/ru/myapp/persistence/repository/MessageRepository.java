package ru.myapp.persistence.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import ru.myapp.persistence.model.Message;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Message> findByMessageId(String messageId);
}
