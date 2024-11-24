package ru.myapp.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import ru.myapp.config.ActivityToggle;

@Component
@RequiredArgsConstructor
@Endpoint(id = "activity")
public class ActivityEndpoint {

    private final ActivityToggle activityToggle;

    @WriteOperation
    public void activityEnable(@Selector Boolean isEnable) {
        activityToggle.setEnabled(isEnable);
    }
}
