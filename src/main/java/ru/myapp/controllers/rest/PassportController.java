package ru.myapp.controllers.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.request.PassportRequestDto;
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

  @PostMapping("/user/{userId}")
  public ResponseEntity<PassportResponseDto> createPassportForUser(
      @PathVariable Integer userId,
      @RequestBody @Valid PassportRequestDto passportRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(passportService.createPassport(userId, passportRequestDto));
  }

  @GetMapping("/{passportId}")
  public ResponseEntity<PassportResponseDto> getPassportById(@PathVariable Integer passportId) {
    return ResponseEntity.ok(passportService.getPasswordById(passportId));
  }
}
