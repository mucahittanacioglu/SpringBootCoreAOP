package com.ts.core.logging;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

@Component
@Aspect
@Order(1)
@ConditionalOnProperty(name = "logging.enabled", havingValue = "true")
public class LoggingAspect {

    //TODO Loglama ile alakalı bbir interface oluştur kullananlar isteiğini yazdırsın
    @Autowired
    private ILoggerConfiguration logger;
    @Autowired
    ApplicationContext applicationContext;
    private ILogging log;

    @Pointcut("@annotation(com.ts.core.logging.Log)")
    public void logAnnotatedMethods() {
        // Pointcut for methods annotated with @Log
    }

    @Around("logAnnotatedMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        var args = joinPoint.getArgs();

        Log logAnnotation = method.getAnnotation(Log.class);

        Class<? extends ILogging> loggerClass = logAnnotation.log();
        log = applicationContext.getBean(loggerClass);
        
        log.setJoinPoint(joinPoint);
        
        // Before executing the method
        for (LogAction action : logAnnotation.action()) {
            handleLogging(action,log);
        }
        // While executing method
        Object result;
        try {

            result = joinPoint.proceed();
            log.setResult(result);

        } catch (Exception e) {
            if(Arrays.stream(logAnnotation.action()).anyMatch(c->c.equals(LogAction.ERROR)))
                logConsumer(logger::error,log.error(e));
//            e.printStackTrace();
            throw e; // Re-throwing the exception to ensure the original flow is maintained
        }

        log.setIsExecuted(true);

        // After executing the method
        for (LogAction action : logAnnotation.action()) {
            handleLogging(action,log);
        }

        log.setResult(null);
        log.setJoinPoint(null);
        log.setIsExecuted(false);
        return result;
    }
    
    private void handleLogging(LogAction action,ILogging log) {

        switch (action) {
            
            case INFO:
                logConsumer(logger::info,log.info());
                break;
            case DEBUG:
                logConsumer(logger::debug,log.debug());
                break;
            case WARNING:
                logConsumer(logger::warn,log.warn());
                break;
            default:
                break;
        }
    }
    void logConsumer(Consumer<String> log, String message){
        if(message != null)log.accept(message);
    }
}

