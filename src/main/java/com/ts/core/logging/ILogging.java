package com.ts.core.logging;

import org.aspectj.lang.ProceedingJoinPoint;

public abstract class ILogging {
    ProceedingJoinPoint joinPoint = null;
    Object result = null;
    boolean isExecuted = false;

    void setIsExecuted(boolean isExecuted){
        this.isExecuted = isExecuted;
    }
    public boolean getIsExecuted(){
        return this.isExecuted;
    }

    void setResult(Object result){
        this.result = result;
    }
    public Object getResult(){
        return this.result;
    }

    void setJoinPoint(ProceedingJoinPoint joinPoint){
        this.joinPoint = joinPoint;
    }

    public ProceedingJoinPoint getJoinPoint(){
        return this.joinPoint;
    }

    public abstract String debug();
    public abstract String info();
    public abstract String warn();
    public abstract String error(Throwable throwable);
}
