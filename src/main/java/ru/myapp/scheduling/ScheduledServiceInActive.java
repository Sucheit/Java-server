package ru.myapp.scheduling;

import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.myapp.config.ActivityToggle;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "activity", name = "enabled", havingValue = "false")
public class ScheduledServiceInActive implements ScheduledService {

    private final ActivityToggle activityToggle;
    private final Counter scheduledInactiveTaskCounter;

    @Async("taskExecutor")
    @Scheduled(fixedRate = 5000)
    public void scheduled() {
        log.info("activity.enable={} Scheduled task: threadID={}", activityToggle.getEnabled(), Thread.currentThread().threadId());
        scheduledInactiveTaskCounter.increment();
    }
}
