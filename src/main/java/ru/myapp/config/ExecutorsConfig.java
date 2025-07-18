package ru.myapp.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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

  @Bean
  public TaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(5);
    taskExecutor.setMaxPoolSize(10);
    taskExecutor.setQueueCapacity(100);
    taskExecutor.setPrestartAllCoreThreads(true);
    taskExecutor.initialize();
    return taskExecutor;
  }

  @Bean TaskExecutor simpleAsyncTaskExecutor() {
    return new SimpleAsyncTaskExecutor();
  }

  @Bean TaskExecutor concurrentTaskExecutor() {
    return new ConcurrentTaskExecutor();
  }

  @Bean TaskExecutor syncTaskExecutor() {
    return new SyncTaskExecutor();
  }
}
