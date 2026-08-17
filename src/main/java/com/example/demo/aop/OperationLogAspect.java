package com.example.demo.aop;


import cn.hutool.core.date.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    // 切点：扫描带有@OperationLog注解的方法
    @Pointcut("execution(* com.example.demo.controller..*(..))")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        log.info(joinPoint.getSignature().getName()+"耗时"+stopWatch.getLastTaskTimeMillis());
        return result;
    }



}
