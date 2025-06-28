package ru.myapp.context;

import jakarta.annotation.PreDestroy;
import java.time.OffsetDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeanToBeDeleted implements DisposableBean, SmartLifecycle {

  private volatile boolean running = false;

  public OffsetDateTime getCurrentTime() {
    return OffsetDateTime.now();
  }

  @PreDestroy
  public void preDestroy() {
    log.info("preDestroy method!");
  }

  @Override
  public void destroy() throws Exception {
    log.info("DisposableBean method!");
  }

  @Override
  public boolean isAutoStartup() {
    log.info("SmartLifeCycle isAutoStartup");
    return false;
  }

  @Override
  public void stop(Runnable callback) {
    log.info("SmartLifeCycle stop(Runnable callback)");
    // Асинхронная остановка
    new Thread(() -> {
      stop();
      callback.run();
    }).start();
  }

  @Override
  public void stop() {
    log.info("SmartLifeCycle stop()");
  }

  @Override
  public int getPhase() {
    log.info("SmartLifeCycle getPhase()");
    return 1;
  }

  @Override
  public void start() {
    log.info("SmartLifeCycle start()");
  }


  @Override
  public boolean isRunning() {
    log.info("SmartLifeCycle isRunning()");
    return running;
  }
}
