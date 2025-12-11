package ru.myapp.service;

import ru.myapp.dto.request.PassportRequestDto;
import ru.myapp.dto.response.PassportResponseDto;

public interface PassportService {

  PassportResponseDto getPasswordByUserId(Integer userId);

  PassportResponseDto getPasswordById(Integer passportId);

  PassportResponseDto createPassport(Integer userId, PassportRequestDto passportRequestDto);
}
