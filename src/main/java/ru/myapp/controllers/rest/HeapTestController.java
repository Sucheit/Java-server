package ru.myapp.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeapTestController {

  @PostMapping(path = "heap-test")
  public String heapTest() {
    String str = "";
    int num = 1_000_000;
    List<String> list = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      str = str + i;
      list.add(str);
    }
    return "heap-test: " + list.size();
  }
}
