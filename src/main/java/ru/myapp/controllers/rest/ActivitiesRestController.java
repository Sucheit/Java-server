package ru.myapp.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.config.ActivityToggle;

@RestController
@RequestMapping(path = "activity")
@RequiredArgsConstructor
public class ActivitiesRestController {

  private final ActivityToggle activityToggle;

  @GetMapping
  public Boolean getIsActive() {
    return activityToggle.isEnabled();
  }
}
