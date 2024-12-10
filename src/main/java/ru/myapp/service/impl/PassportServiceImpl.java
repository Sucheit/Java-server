package ru.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.myapp.dto.request.PassportRequestDto;
import ru.myapp.dto.response.PassportResponseDto;
import ru.myapp.error.BadRequestException;
import ru.myapp.error.NotFoundException;
import ru.myapp.mappers.PassportMapper;
import ru.myapp.persistence.model.Passport;
import ru.myapp.persistence.model.User;
import ru.myapp.persistence.repository.PassportRepository;
import ru.myapp.persistence.repository.UserRepository;
import ru.myapp.service.PassportService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    private final PassportMapper passportMapper;

    private final UserRepository userRepository;

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

    @Override
    public PassportResponseDto createPassport(Integer userId, PassportRequestDto passportRequestDto) {
        if (passportRepository.existsById(userId)) {
            throw new BadRequestException("Passport id=%s already exists".formatted(userId));
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id=%s not found".formatted(userId)));
        Passport passport = new Passport();
        passport.setUser(user);
        passport.setSerialNumber(passportRequestDto.serialNumber());
        passport = passportRepository.save(passport);
        return passportMapper.mapToPassportResponseDto(passport);
    }
}
