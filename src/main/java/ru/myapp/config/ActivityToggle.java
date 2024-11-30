package ru.myapp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@RefreshScope
@ConfigurationProperties(prefix = "activity")
public class ActivityToggle {

    private boolean enabled;
    private boolean kafkaSender;
}
