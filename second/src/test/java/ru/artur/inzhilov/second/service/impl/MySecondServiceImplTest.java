package ru.artur.inzhilov.second.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.artur.inzhilov.second.service.MySecondService;

class MySecondServiceImplTest {

  private final MySecondService mySecondService = new MySecondServiceImpl();

  @Test
  public void testEvaluate() {
    int whatever = mySecondService.evaluate("whatever");
    assertEquals(1,whatever);
  }
}