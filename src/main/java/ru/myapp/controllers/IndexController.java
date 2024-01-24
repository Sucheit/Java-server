package ru.myapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.myapp.aop.Loggable;

@Controller
public class IndexController {

    @Loggable
    @GetMapping(path = "/")
    public String index() {
        return "index.html";
    }
}

