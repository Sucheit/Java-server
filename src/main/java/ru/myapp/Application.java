package ru.myapp;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@EnableAsync
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@EnableSchedulerLock(defaultLockAtMostFor = "10s")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
