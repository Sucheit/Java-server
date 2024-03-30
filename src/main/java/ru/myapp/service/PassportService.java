package ru.myapp.service;

import ru.myapp.dto.response.PassportResponseDto;

public interface PassportService {

    PassportResponseDto getPasswordByUserId(Integer userId);

    PassportResponseDto getPasswordById(Integer passportId);
}
