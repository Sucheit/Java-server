package ru.myapp.context;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAwareService implements ApplicationContextAware {

  private ApplicationContext context;
  private Environment environment;
  @Autowired
  private ConfigurableApplicationContext configurableApplicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }

  @PostConstruct
  public void postConstruct() {
    environment = context.getEnvironment();
  }

  public Class<?> getClassByName(String beanName) {
    Object bean = context.getBean(beanName);
    return bean.getClass();
  }

  public String getProperty(String key) {
    return environment.getProperty(key);
  }

  public List<String> getActiveProfiles() {
    return Arrays.stream(environment.getActiveProfiles()).toList();
  }

  public MyPrototypeBean getPrototype() {
    return Optional.of(context.getBean("myPrototypeBean"))
        .filter(MyPrototypeBean.class::isInstance)
        .map(MyPrototypeBean.class::cast)
        .orElseThrow(() -> new RuntimeException("bean is not MyPrototypeBean type!"));
  }

  public void recreateDeletedBean() {
    DefaultListableBeanFactory beanFactory =
        (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
    String beanName = "beanToBeDeleted";
    try {
      Object newInstance = BeanToBeDeleted.class.getDeclaredConstructor().newInstance();
      beanFactory.registerSingleton(beanName, newInstance);
      beanFactory.initializeBean(newInstance, beanName);
    } catch (Exception e) {
      throw new RuntimeException("Failed to reinitialize bean " + beanName, e);
    }
  }
}
