package com.ts.core.validation;

import com.ts.core.entities.IEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Aspect
@Component
@Order(2)
public class ValidationAspect {

    @Autowired
    private ApplicationContext applicationContext;

    //Before yapılabilir
    @Around("@annotation(validateEntity)")
    public Object validateWithValidator(ProceedingJoinPoint joinPoint, Validate validateEntity) throws Throwable {
        IValidator<IEntity> validator = (IValidator<IEntity>) applicationContext.getBean(validateEntity.value());
        // TODO add support for multiple params
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof IEntity) {
            validator.validate((IEntity) args[0]);
        }
        return joinPoint.proceed();

    }
}