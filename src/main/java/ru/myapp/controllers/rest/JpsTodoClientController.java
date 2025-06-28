package ru.myapp.controllers.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arthur.inzhilov.jps.todo.JpsTodoClient;
import ru.arthur.inzhilov.jps.todo.ToDo;

@RestController
@Profile("ift")
@RequiredArgsConstructor
@RequestMapping("/jpsTodoClient")
public class JpsTodoClientController {

  private final JpsTodoClient jpsTodoClient;

  @GetMapping
  public List<ToDo> findAllTodos() {
    return jpsTodoClient.findAll();
  }

  @GetMapping("/{id}")
  public ToDo getTodoById(@PathVariable(name = "id") Integer id) {
    return jpsTodoClient.findById(id);
  }
}
