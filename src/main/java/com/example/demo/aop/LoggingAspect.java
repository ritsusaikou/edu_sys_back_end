package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// Slf4j是对log.info的注释
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.demo.controller..*(..))")
    public void logPointCut() {
    }
//    @Around(value = "logPointCut()")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
//        Object[] args = joinPoint.getArgs();
//        String methodName = joinPoint.getSignature().getName();
//        log.info(">>kkk {}() - {}",methodName, Arrays.toString(args));
//        Object result = joinPoint.proceed();
//        log.info("<<kkk {}() - {}",methodName,result);
//        return result;
//    }

    @Before(value = "logPointCut()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.info(">>kkk {}() - {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "logPointCut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("<<kkk {}() - {}", methodName, result);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.info("<<kkk {}() - {}", exception.getMessage());
    }


}
