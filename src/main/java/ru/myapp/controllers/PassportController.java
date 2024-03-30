package ru.myapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.response.PassportResponseDto;
import ru.myapp.service.PassportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/passports")
public class PassportController {

    private final PassportService passportService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<PassportResponseDto> getPassportByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(passportService.getPasswordByUserId(userId));
    }

    @GetMapping("/{passportId}")
    public ResponseEntity<PassportResponseDto> getPassportById(@PathVariable Integer passportId) {
        return ResponseEntity.ok(passportService.getPasswordById(passportId));
    }
}
