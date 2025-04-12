package ru.myapp.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.service.MessageService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/batch")
public class BatchMessageSendController {

    private final MessageService messageService;

    @GetMapping("/send/{amount}")
    public String sendBatchMessages(@PathVariable("amount") int amount) {
        messageService.sendMessages(amount);
        return "Batch sent!";
    }
}
