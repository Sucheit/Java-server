package ru.myapp.service.impl;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.myapp.scheduling.ShedLockTaskService;
import ru.myapp.service.ShedLockTaskStarter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShedLockTaskStarterImpl implements ShedLockTaskStarter {

  private final ShedLockTaskService shedLockTaskService;

  @Override
  public OffsetDateTime startScheduledTaskWithReturn() {
    OffsetDateTime offsetDateTime = shedLockTaskService.scheduledTaskWithReturn();
    log.info("Результат scheduledTaskWithReturn: {}", offsetDateTime);
    return offsetDateTime;
  }
}
