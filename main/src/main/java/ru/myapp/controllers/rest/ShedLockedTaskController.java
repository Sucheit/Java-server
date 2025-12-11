package ru.myapp.controllers.rest;


import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.service.ShedLockTaskStarter;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "shedlock")
public class ShedLockedTaskController {

  private final ShedLockTaskStarter shedLockTaskStarter;

  @GetMapping
  public OffsetDateTime getOffsetDateTime() {
    return shedLockTaskStarter.startScheduledTaskWithReturn();
  }
}
