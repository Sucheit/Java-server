package ru.myapp.unit.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.myapp.controllers.rest.VersionsController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VersionsController.class)
public class VersionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testA1() {
        testEndpoint("/v1/a", "a1");
    }

    @Test
    public void testA2() {
        testEndpoint("/v2/a", "a2");
    }

    @Test
    public void testA3() {
        testEndpoint("/v3/a", "a3");
    }

    @Test
    public void testB1() {
        testEndpoint("/v1/b", "b1");
    }

    @Test
    public void testB2() {
        testEndpoint("/v2/b", "b1");
    }

    @Test
    public void testB3() {
        testEndpoint("/v3/b", "b1");
    }

    @SneakyThrows
    private void testEndpoint(String path, String expectedResult) {
        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult))
                .andReturn();
    }
}
