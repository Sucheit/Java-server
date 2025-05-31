package ru.myapp.controllers.rest;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.response.MessageResponseDto;
import ru.myapp.service.MessageService;
import ru.myapp.utils.Utils;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "messages")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public List<MessageResponseDto> getMessages(
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size) {
        return messageService.getMessages(Utils.getPageRequest(from, size, "id", true));
    }

    @GetMapping("/{message-id}")
    public MessageResponseDto getMessageById(
            @PathVariable(name = "message-id") String messageId
    ) {
        return messageService.getMessageByMessageId(messageId);
    }
}
