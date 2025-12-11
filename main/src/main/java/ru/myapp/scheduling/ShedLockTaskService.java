package ru.myapp.scheduling;

import java.time.OffsetDateTime;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShedLockTaskService {

  @SchedulerLock(name = "scheduledTaskWithReturn", lockAtLeastFor = "50000s", lockAtMostFor = "50000s")
  public OffsetDateTime scheduledTaskWithReturn() {
    return OffsetDateTime.now();
  }
}
