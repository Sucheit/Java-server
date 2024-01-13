package ru.myapp.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectLoggable {

    private static final Logger logger = LogManager.getLogger(AspectLoggable.class);

    @Around("@annotation(ru.myapp.aop.Loggable)")
    public Object methodLoggingAndExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Method " + methodName + " called with args " + Arrays.toString(args));

        final long start = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        final long executionTime = System.currentTimeMillis() - start;

        logger.info("Method " + methodName + " returned " + result);
        logger.info(methodName + " executed in " + executionTime + "ms");
        return result;
    }
}
