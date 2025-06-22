package ru.myapp.config.remover;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeanRemover {

    private final ConfigurableApplicationContext context;

    public void removeSingletonBean(String beanName) {
        DefaultListableBeanFactory beanFactory =
                (DefaultListableBeanFactory) context.getBeanFactory();
        beanFactory.destroySingleton(beanName);
        beanFactory.removeBeanDefinition(beanName);
        log.info("Bean name={} is deleted!", beanName);
    }
}
