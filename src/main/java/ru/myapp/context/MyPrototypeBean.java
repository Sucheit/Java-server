package ru.myapp.context;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component("myPrototypeBean")
@Scope("prototype")
public class MyPrototypeBean {

  private final OffsetDateTime createTime;

  public MyPrototypeBean() {
    this.createTime = OffsetDateTime.now();
  }
}
