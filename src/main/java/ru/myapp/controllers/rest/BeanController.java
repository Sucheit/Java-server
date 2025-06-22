package ru.myapp.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.config.remover.BeanRemover;
import ru.myapp.config.remover.BeanToBeDeleted;

import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "bean")
public class BeanController {

    private final BeanRemover beanRemover;

    private final BeanToBeDeleted beanToBeDeleted;

    @GetMapping
    public OffsetDateTime getTime() {
        return beanToBeDeleted.getCurrentTime();
    }

    @PostMapping(path = "/{beanName}")
    public String deleteBean(@PathVariable(name = "beanName") String beanName) {
        beanRemover.removeSingletonBean(beanName);
        return beanName + " is deleted from context";
    }
}
