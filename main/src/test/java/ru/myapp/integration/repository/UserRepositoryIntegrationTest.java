package ru.myapp.integration.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.myapp.persistence.model.User;
import ru.myapp.persistence.repository.UserRepository;

@Disabled
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine3.19");

    @Autowired
    private UserRepository userRepository;

    @Test
    public void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    @Rollback
    public void createUser() {
        User savedUser = userRepository.save(new User("Tom", "Hill"));

        assertNotNull(savedUser);
        assertEquals(22, savedUser.getId());
        assertEquals("Tom", savedUser.getFirstName());
        assertEquals("Hill", savedUser.getLastName());
    }

    @Test
    @Rollback
    public void deleteUser() {
        userRepository.deleteById(1);

        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(20, users.size());
        assertEquals(2, users.getFirst().getId());
        assertEquals("Natasha", users.getFirst().getFirstName());
        assertEquals("Romanoff", users.getFirst().getLastName());
    }

    @Test
    @Rollback
    public void getAllUser() {
        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(21, users.size());
        assertEquals(1, users.getFirst().getId());
        assertEquals("Steve", users.getFirst().getFirstName());
        assertEquals("Rogers", users.getFirst().getLastName());
        assertEquals(2, users.get(1).getId());
        assertEquals("Natasha", users.get(1).getFirstName());
        assertEquals("Romanoff", users.get(1).getLastName());
        assertEquals(21, users.get(20).getId());
        assertEquals("Jane", users.get(20).getFirstName());
        assertEquals("Foster", users.get(20).getLastName());
    }
}
