package ru.myapp.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
        String className = joinPoint.getTarget().toString();
        Object[] args = joinPoint.getArgs();

        logger.info("Class: {}, Method: {}() called with agrs: {}", className, methodName, Arrays.toString(args));

        final long start = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        final long executionTime = System.currentTimeMillis() - start;

        logger.info("Class: {}, Method: {}()  returned: {}", className, methodName, result);
        logger.info("Class: {}, Method: {}()  executed in {} ms", className, methodName, executionTime);
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

    @Pointcut("@annotation(ru.myapp.aspect.AfterReturningAnnotation)")
    private void aspectAnnotationMethod() {
    }

    @AfterReturning(value = "aspectAnnotationMethod()", returning = "object")
    public void aspectAnnotationAspect(JoinPoint joinPoint, Object object) {
        logger.info("AfterReturningAnnotation: Method {}() returned: {}", joinPoint.getSignature().getName(), object);
    }
}
