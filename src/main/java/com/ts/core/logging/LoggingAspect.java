package com.ts.core.logging;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@Order(1)
@ConditionalOnProperty(name = "logging.enabled", havingValue = "true")
public class LoggingAspect {

    @Autowired
    private ILoggerConfiguration logger;

    @Pointcut("@annotation(com.ts.core.logging.Log)")
    public void logAnnotatedMethods() {
        // Pointcut for methods annotated with @Log
    }

    @Around("logAnnotatedMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Log logAnnotation = method.getAnnotation(Log.class);

        // Before executing the method
        for (LogAction action : logAnnotation.action()) {
            handleLoggingBeforeMethod(method, action);
        }

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            if(Arrays.stream(logAnnotation.action()).anyMatch(c->c.equals(LogAction.ERROR)))
                logger.error("Exception in method: " + method.getName(), e);
            throw e; // Re-throwing the exception to ensure the original flow is maintained
        }

        // After executing the method
        for (LogAction action : logAnnotation.action()) {
            handleLoggingAfterMethod(method, result, action);
        }

        return result;
    }

    private void handleLoggingBeforeMethod(Method method, LogAction action) {
        switch (action) {
            case INFO:
                logger.info("Entering method: " + method.getName());
                break;
            case DEBUG:
                logger.debug("Executing method: " + method.getName());
                break;
            case WARNING:
                logger.warn("Caution! Entering method: " + method.getName());
                break;
            default:
                break;
        }
    }

    private void handleLoggingAfterMethod(Method method, Object result, LogAction action) {
        switch (action) {
            case INFO:
                logger.info("Exiting method: " + method.getName());
                break;
            case DEBUG:
                logger.debug("Method result for " + method.getName() + ": " + result);
                break;
            case WARNING:
                logger.warn("Finished execution with caution in method: " + method.getName());
                break;
            default:
                break;
        }
    }
}
