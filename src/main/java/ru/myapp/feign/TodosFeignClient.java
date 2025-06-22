package ru.myapp.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.myapp.config.FeignClientConfig;
import ru.myapp.dto.response.ToDo;

import java.util.List;

@Profile({"local"})
@FeignClient(name = "${feign.client.name}",
        url = "${feign.client.url}",
        path = "/todos",
        configuration = {FeignClientConfig.class})
public interface TodosFeignClient {

    @GetMapping()
    ResponseEntity<List<ToDo>> getAllToDos();

    @GetMapping("/{id}")
    ResponseEntity<ToDo> getToDoById(@PathVariable(name = "id") Integer id);
}
