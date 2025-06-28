package ru.myapp.controllers.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.context.ApplicationAwareService;
import ru.myapp.context.MyPrototypeBean;

@RestController
@RequestMapping(path = "context")
@RequiredArgsConstructor
public class ApplicationAwareController {

  private final ApplicationAwareService applicationAwareService;

  @GetMapping(path = "/class/{name}")
  public String getClass(@PathVariable(name = "name") String name) {
    Class<?> clazz = applicationAwareService.getClassByName(name);
    return clazz.toString();
  }

  @GetMapping(path = "/property/{key}")
  public String getProperty(@PathVariable(name = "key") String key) {
    return applicationAwareService.getProperty(key);
  }

  @GetMapping(path = "/profiles")
  public List<String> getActiveProfiles() {
    return applicationAwareService.getActiveProfiles();
  }

  @GetMapping(path = "/prototype")
  public MyPrototypeBean getPrototype() {
    return applicationAwareService.getPrototype();
  }

  @PostMapping(path = "/create")
  public String recreateBean() {
    applicationAwareService.recreateDeletedBean();
    return "BeanToBeDeleted is recreated!";
  }
}
