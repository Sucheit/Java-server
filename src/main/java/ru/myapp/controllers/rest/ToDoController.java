package ru.myapp.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.response.ToDo;
import ru.myapp.feign.TodosFeignClient;

import java.util.List;

@RestController
@Profile("local")
@RequiredArgsConstructor
@RequestMapping(path = "/todos")
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
