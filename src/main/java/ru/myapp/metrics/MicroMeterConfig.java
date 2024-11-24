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
    public Counter scheduledActiveTaskCounter() {
        return Counter.builder("scheduled_active_tasks.counter")
                .description("Кол-во выполненных активных задач.")
                .register(registry);
    }

    @Bean
    public Counter scheduledInactiveTaskCounter() {
        return Counter.builder("scheduled_inactive_tasks.counter")
                .description("Кол-во выполненных не активных задач.")
                .register(registry);
    }
}
