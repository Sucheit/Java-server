package ru.myapp.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class Config {

  @Bean
  public ExecutorService taskExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  @Bean
  public ScheduledExecutorService itemsScheduledExecutorService() {
    return Executors.newSingleThreadScheduledExecutor();
  }

  @Bean
  public ScheduledExecutorService usersScheduledExecutorService() {
    return Executors.newSingleThreadScheduledExecutor();
  }
}
