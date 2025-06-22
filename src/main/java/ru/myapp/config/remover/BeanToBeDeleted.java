package ru.myapp.config.remover;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@Component
public class BeanToBeDeleted implements DisposableBean {

    public OffsetDateTime getCurrentTime() {
        return OffsetDateTime.now();
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy method!");
    }

    @Override
    public void destroy() throws Exception {
        log.info("DisposableBean method!");
    }

}
