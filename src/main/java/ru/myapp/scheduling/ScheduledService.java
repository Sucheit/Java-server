package ru.myapp.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduledService {

    @Async("taskExecutor")
    @Scheduled(fixedRate = 15000)
    public void scheduled() {
        log.info("Scheduled task: {}", Thread.currentThread().getName());
    }
}
