package ru.myapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.request.ItemRequestDto;
import ru.myapp.dto.response.ItemResponseDto;
import ru.myapp.persistence.model.ItemFullInfo;
import ru.myapp.persistence.model.ItemProjection;
import ru.myapp.persistence.model.ItemView;
import ru.myapp.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public String sendItemToKafka(@RequestBody @Valid ItemRequestDto itemRequestDto) {
        itemService.sendToKafka(itemRequestDto);
        return "Item sent to kafka";
    }

    @GetMapping(path = "/{itemId}")
    public ItemResponseDto getItemById(@PathVariable(name = "itemId") Integer itemId) {
        return itemService.getItemById(itemId);
    }

    @DeleteMapping(path = "/{itemId}")
    public String deleteItemById(@PathVariable(name = "itemId") Integer itemId) {
        itemService.deleteItem(itemId);
        return "Item id=%s deleted".formatted(itemId);
    }

    @PutMapping(path = "/{itemId}")
    public ItemResponseDto putItem(@RequestBody ItemRequestDto itemRequestDto, @PathVariable Integer itemId) {
        return itemService.putItem(itemRequestDto, itemId);
    }

    @GetMapping(path = "/view/{itemId}")
    public ItemView getViewItem(@PathVariable(name = "itemId") Integer itemId) {
        return itemService.getItemView(itemId);
    }

    @GetMapping(path = "/info/{itemId}")
    public ItemFullInfo getItemFullInfo(@PathVariable(name = "itemId") Integer itemId) {
        return itemService.getFullInfo(itemId);
    }

    @GetMapping(path = "/projection/{itemId}")
    public ItemProjection getItemProjection(@PathVariable(name = "itemId") Integer itemId) {
        return itemService.getItemProjection(itemId);
    }
}
