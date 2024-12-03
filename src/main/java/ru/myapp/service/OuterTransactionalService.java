package ru.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OuterTransactionalService {

    private final InnerTransactionService innerTransactionService;

    @Transactional
    public void outerTransaction() {
        MDC.put("transaction.owner", Thread.currentThread().getName());
        log.info("outerTransaction");
        innerTransactionService.innerTransaction();
    }
}
