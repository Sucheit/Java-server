package ru.myapp.scheduling;

import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.myapp.config.ActivityToggle;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "activity", name = "enabled", havingValue = "true")
public class ScheduledServiceActive {

    private final ActivityToggle activityToggle;
    private final Counter scheduledTaskCounter;

    @Async("taskExecutor")
    @Scheduled(fixedRate = 5000)
    @SchedulerLock(name = "scheduledTask", lockAtMostFor = "30s", lockAtLeastFor = "15s")
    public void scheduledTask() {
        log.info(""" 
                        activity.enable={} Scheduled task: threadID={}, time={}
                        """,
                activityToggle.isEnabled(),
                Thread.currentThread().threadId(),
                OffsetDateTime.now());
        scheduledTaskCounter.increment();
    }
}
