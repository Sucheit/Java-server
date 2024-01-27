package ru.myapp.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.myapp.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

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
    public void createUser() {
        User savedUser = userRepository.save(new User("Tom", "Hill"));

        assertNotNull(savedUser);
        assertEquals(3, savedUser.getId());
        assertEquals("Tom", savedUser.getFirstName());
        assertEquals("Hill", savedUser.getLastName());
    }

    @Test
    public void deleteUser() {
        userRepository.deleteById(1);

        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(2, users.get(0).getId());
        assertEquals("Natasha", users.get(0).getFirstName());
        assertEquals("Romanoff", users.get(0).getLastName());
    }

    @Test
    public void getAllUser() {
        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("Steve", users.get(0).getFirstName());
        assertEquals("Rogers", users.get(0).getLastName());
        assertEquals(2, users.get(1).getId());
        assertEquals("Natasha", users.get(1).getFirstName());
        assertEquals("Romanoff", users.get(1).getLastName());
    }
}
