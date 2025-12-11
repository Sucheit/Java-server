package ru.myapp.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.service.OuterTransactionalService;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {

  private final OuterTransactionalService outerTransactionalService;

  @GetMapping(path = "/transactional")
  public String transactional(@RequestHeader(name = "trace-id") String traceId) {
    MDC.put("trace.id", traceId);
    outerTransactionalService.outerTransaction();
    return "Transaction is completed!";
  }
}
