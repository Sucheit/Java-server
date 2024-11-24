package ru.myapp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@RefreshScope
@ConditionalOnProperty(prefix = "activity")
public class ActivityToggle {

    private Boolean enabled;
}
