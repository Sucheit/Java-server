package ru.myapp.config.kafka;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCustomRetryer implements Retryer {

  private final int maxAttempts;
  private final long backoff;
  private int attempt;

  public MyCustomRetryer() {
    this(100, 5); // пауза 100мс, 5 попыток
  }

  public MyCustomRetryer(long backoff, int maxAttempts) {
    this.backoff = backoff;
    this.maxAttempts = maxAttempts;
    this.attempt = 1;
  }

  @Override
  public void continueOrPropagate(RetryableException e) {
    log.info("Попытка запроса №{}", attempt);
    if (attempt++ >= maxAttempts) {
      throw e;
    }

    try {
      Thread.sleep(backoff);
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
      throw e;
    }
  }

  @Override
  public Retryer clone() {
    return new MyCustomRetryer(backoff, maxAttempts);
  }
}
