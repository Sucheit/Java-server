package ru.myapp.service;

import java.util.concurrent.ScheduledExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContextEventsService {

  private final ScheduledExecutorService itemsScheduledExecutorService;
  private final ScheduledExecutorService usersScheduledExecutorService;

  @EventListener(ContextRefreshedEvent.class)
  public void contextRefreshedEvent() {
    log.info("contextRefreshedEvent!");
  }

  @EventListener(ContextClosedEvent.class)
  public void contextClosedEvent() {
    log.info("contextClosedEvent!");
    itemsScheduledExecutorService.shutdown();
    usersScheduledExecutorService.shutdown();
  }
}
