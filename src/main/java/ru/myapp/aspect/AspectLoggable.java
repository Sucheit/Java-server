package ru.myapp.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectLoggable {

    private static final Logger logger = LogManager.getLogger(AspectLoggable.class);

    @Pointcut("within(ru.myapp.controllers.*)")
    private void controllersMethods() {
    }

    @Around("controllersMethods()")
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

    @Pointcut("execution(* ru.myapp.error.ErrorHandler.*(..))")
    private void loggingErrorMessage() {
    }

    @Before("loggingErrorMessage()")
    public void beforeThrowException(JoinPoint joinPoint) {
        Object[] arg = joinPoint.getArgs();
        String exceptionInfo = arg[0].toString();
        logger.error("Exception: {}", exceptionInfo);
    }
}
