package ru.myapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.response.ToDo;
import ru.myapp.feign.TodosFeignClient;

import java.util.List;

@Profile("prod")
@RestController
@RequestMapping(path = "/todos")
@RequiredArgsConstructor
public class ToDoController {

    private final TodosFeignClient todosFeignClient;

    @GetMapping
    public List<ToDo> getAllToDos() {
        return todosFeignClient.getAllToDos().getBody();
    }

    @GetMapping(path = "/{id}")
    public ToDo getToDoById(@PathVariable(name = "id") Integer id) {
        return todosFeignClient.getToDoById(id).getBody();
    }
}
