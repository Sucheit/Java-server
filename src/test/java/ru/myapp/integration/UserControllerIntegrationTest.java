package ru.myapp.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.myapp.dto.UserRequestDto;
import ru.myapp.dto.UserResponseDto;
import ru.myapp.dto.UserResponseDtoShort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class UserControllerIntegrationTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    public void getAllUsers() {
        String url = "/users";
        UserResponseDtoShort[] users = testRestTemplate.getForObject(url, UserResponseDtoShort[].class);

        assertNotNull(users);
        assertEquals(2, users.length);
        assertEquals(1, users[0].id());
        assertEquals("Steve", users[0].firstName());
        assertEquals("Rogers", users[0].lastName());
        assertEquals(2, users[1].id());
        assertEquals("Natasha", users[1].firstName());
        assertEquals("Romanoff", users[1].lastName());
    }

    @Test
    @Order(2)
    public void getUserById() {
        String url = "/users/2";

        ResponseEntity<UserResponseDto> response = testRestTemplate.getForEntity(url, UserResponseDto.class);

        assertNotNull(response);
        UserResponseDto user = response.getBody();
        assertNotNull(user);
        assertEquals(2, user.id());
        assertEquals("Natasha", user.firstName());
        assertEquals("Romanoff", user.lastName());
    }

    @Test
    @Order(3)
    public void createUser() {
        String url = "/users";
        UserRequestDto userRequestDto = new UserRequestDto("Tony", "Stark");
        ResponseEntity<UserResponseDto> response =
                testRestTemplate.postForEntity(url, userRequestDto, UserResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        UserResponseDto user = response.getBody();
        assertNotNull(user);
        assertEquals(3, user.id());
        assertEquals("Tony", user.firstName());
        assertEquals("Stark", user.lastName());
    }

    @Test
    @Order(4)
    public void updateUser() {
        String url = "/users/2";
        UserRequestDto userRequestDto = new UserRequestDto("Natalia", "Romanova");

        testRestTemplate.put(url, userRequestDto, UserResponseDto.class);

        ResponseEntity<UserResponseDto> response = testRestTemplate.getForEntity(url, UserResponseDto.class);

        assertNotNull(response);
        UserResponseDto user = response.getBody();
        assertNotNull(user);
        assertEquals(2, user.id());
        assertEquals("Natalia", user.firstName());
        assertEquals("Romanova", user.lastName());
    }

    @Test
    @Order(5)
    public void deleteUser() {
        String ulr = "/users/1";

        testRestTemplate.delete(ulr);

        String url = "/users";
        UserResponseDtoShort[] users = testRestTemplate.getForObject(url, UserResponseDtoShort[].class);

        assertNotNull(users);
        assertEquals(2, users.length);
    }
}
