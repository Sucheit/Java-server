package ru.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "myAuditorProvider", modifyOnCreate = false)
public class AuditConfig {

    @Bean(name = "myAuditorProvider")
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("ru.arthur.inzhilov");
    }
}
