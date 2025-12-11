package ru.myapp.context;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeanRemover implements SmartLifecycle {

  private final ConfigurableApplicationContext context;
  private final List<SmartLifecycle> components;
  private volatile boolean running;

  public void removeSingletonBean(String beanName) {
    DefaultListableBeanFactory beanFactory =
        (DefaultListableBeanFactory) context.getBeanFactory();
    beanFactory.destroySingleton(beanName);
    beanFactory.removeBeanDefinition(beanName);
    log.info("Bean name={} is deleted!", beanName);
  }

  @Override
  public void start() {
    components.stream()
        .sorted(Comparator.comparingInt(SmartLifecycle::getPhase))
        .forEach(SmartLifecycle::start);
    running = true;
  }

  @Override
  public void stop() {
    components.stream()
        .sorted(Comparator.comparingInt(SmartLifecycle::getPhase).reversed())
        .forEach(SmartLifecycle::stop);
    running = false;
  }

  @Override
  public boolean isRunning() {
    return running;
  }
}
