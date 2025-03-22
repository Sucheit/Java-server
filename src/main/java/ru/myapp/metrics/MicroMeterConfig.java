package ru.myapp.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MicroMeterConfig {

    private final MeterRegistry registry;

    @Bean
    public Counter scheduledTaskCounter() {
        return Counter.builder("scheduled_tasks.counter")
                .description("Кол-во выполненных активных задач.")
                .register(registry);
    }

    @Bean
    public Counter kafkaValidationErrorsCounter() {
        return Counter.builder("kafka_validation_error.counter")
                .description("Кол-во ошибок валидации кафки.")
                .register(registry);
    }
}
