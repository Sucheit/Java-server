package ru.myapp.controllers.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.service.ShedLockTaskStarter;

import java.time.OffsetDateTime;

@RequestMapping(path = "shedlock")
@RestController
@RequiredArgsConstructor
public class ShedLockedTaskController {

    private final ShedLockTaskStarter shedLockTaskStarter;

    @GetMapping
    public OffsetDateTime getOffsetDateTime() {
        return shedLockTaskStarter.startScheduledTaskWithReturn();
    }
}
