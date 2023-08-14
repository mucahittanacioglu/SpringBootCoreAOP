package com.ts.core.caching;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(3)
@ConditionalOnProperty(name = "caching.enabled", havingValue = "true")
public class CachingAspect {

    @Autowired
    private ICacheConfiguration ICacheConfiguration;

    @Around("@annotation(cacheable)")
    public Object cacheAroundAdvice(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        String key;
        if (!cacheable.keyExpression().isEmpty()) {
            // Use SpEL expression to derive key
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            EvaluationContext context = new StandardEvaluationContext();
            String[] paramNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
             key = new SpelExpressionParser()
                    .parseExpression(cacheable.keyExpression())
                    .getValue(context, String.class);
        } else {
            // Use the static key
            key = cacheable.key();
        }

        String cacheName = cacheable.cacheName();

        switch (cacheable.action()) {
            case READ:
                // Attempt to get the cached value
                Object cachedValue = ICacheConfiguration.getFromCache(cacheName, key, Object.class);
                if (cachedValue != null) {
                    return cachedValue;
                }
                // Otherwise, proceed with the method execution
                Object result = joinPoint.proceed();
                ICacheConfiguration.addToCache(cacheName, key, result);
                return result;
            case UPDATE:
                Object updateResult = joinPoint.proceed();
                ICacheConfiguration.updateCache(cacheName, key, updateResult);
                return updateResult;

            case DELETE:
                ICacheConfiguration.evictCacheByKey(cacheName, key);
                return joinPoint.proceed();

            default:
                // If no action specified, just proceed with the method execution
                return joinPoint.proceed();
        }
    }

}
