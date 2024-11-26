package ru.myapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class VersionsController {

    private static final int LATEST_VERSION = 3;

    @GetMapping(path = "/v{version}/a")
    public String getA(@PathVariable Integer version) {
        return switch (version) {
            case 1 -> "a1";
            case 2 -> "a2";
            case LATEST_VERSION -> "a3";
            default -> throw new IllegalStateException("Unexpected value: " + version);
        };
    }

    @GetMapping(path = "/v{version}/b")
    public String getB(@PathVariable Integer version) {
        return switch (version) {
            case 1, 2, LATEST_VERSION -> "b1";
            default -> throw new IllegalStateException("Unexpected value: " + version);
        };
    }
}
