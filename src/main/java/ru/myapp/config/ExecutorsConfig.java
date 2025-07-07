package ru.myapp.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class ExecutorsConfig {

  @Bean
  @Primary
  public ExecutorService newVirtualThreadPerTaskExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  @Bean
  public ExecutorService newFixedThreadPool() {
    return Executors.newFixedThreadPool(30);
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
