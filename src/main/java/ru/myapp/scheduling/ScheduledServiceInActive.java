package ru.myapp.scheduling;

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
@ConditionalOnProperty(name = "activity.enabled", havingValue = "false")
public class ScheduledServiceInActive implements ScheduledService {

    private final ActivityToggle activityToggle;

    @Async("taskExecutor")
    @Scheduled(fixedRate = 5000)
    public void scheduled() {
        log.info("activity.enable={} Scheduled task: threadID={}", activityToggle.getEnabled(), Thread.currentThread().threadId());
    }
}
