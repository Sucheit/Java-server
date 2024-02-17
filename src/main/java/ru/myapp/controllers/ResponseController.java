package ru.myapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.Response;
import ru.myapp.service.GroupService;

@RestController
@RequestMapping("/response")
public class ResponseController {

    private final GroupService groupService;

    public ResponseController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public Response getResponse() {
        return groupService.getResponse();
    }
}
