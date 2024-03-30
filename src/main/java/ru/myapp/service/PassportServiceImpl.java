package ru.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.myapp.dto.response.PassportResponseDto;
import ru.myapp.error.NotFoundException;
import ru.myapp.mappers.PassportMapper;
import ru.myapp.model.Passport;
import ru.myapp.repository.PassportRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    private final PassportMapper passportMapper;

    @Override
    public PassportResponseDto getPasswordByUserId(Integer userId) {
        Passport passport = passportRepository.getPassportsByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Passport for user with id=%s not found.".formatted(userId)));
        log.info("got passport: {}", passport);
        return passportMapper.mapToPassportResponseDto(passport);
    }

    @Override
    public PassportResponseDto getPasswordById(Integer passportId) {
        Passport passport = passportRepository.getPassportsById(passportId)
                .orElseThrow(() -> new NotFoundException("Passport id=%s not found.".formatted(passportId)));
        log.info("got passport: {}", passport);
        return passportMapper.mapToPassportResponseDto(passport);
    }
}
