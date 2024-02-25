package ru.myapp.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.myapp.dto.request.ToDo;

import java.util.List;

@FeignClient(name = "${feign.client.name}", url = "${feign.client.url}", path = "/todos")
public interface TodosFeignClient {

    @GetMapping()
    ResponseEntity<List<ToDo>> getAllToDos();

    @GetMapping("/{id}")
    ResponseEntity<ToDo> getToDoById(@PathVariable(name = "id") Integer id);
}
