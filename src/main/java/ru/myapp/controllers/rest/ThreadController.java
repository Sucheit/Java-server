package ru.myapp.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadController {

    @GetMapping(value = "thread")
    public String getThread() {
        return "Current thread: %s".formatted(Thread.currentThread().toString());
    }
}
