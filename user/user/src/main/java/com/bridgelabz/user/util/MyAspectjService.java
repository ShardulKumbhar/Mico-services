package com.bridgelabz.user.util;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MyAspectjService {

    @Before(value = "execution(* com.bridgelabz.user.service.UserService.*(..))")
    public void Before(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getSignature().getName());
    }

    @After(value = "execution(* com.bridgelabz.user.service.UserService.*(..))")
    public void After(JoinPoint joinPoint) {
        System.out.println("After: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "execution(* com.bridgelabz.user.service.UserService.*(..))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        System.out.println("After Returning method:" + joinPoint.getSignature());
    }

    @AfterThrowing(value = "execution(* com.bridgelabz.user.service.UserService.*(..))", throwing = "exec")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception exec) {
        System.out.println("After Throwing Exception in method:" + joinPoint.getSignature());
        System.out.println("Exception is:" + exec.getMessage());
    }

    @Around(value = "execution(* com.bridgelabz.user.service.UserService.*(..))")
    public void aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("The method aroundAdvice() before invocation of the method: " + jp.getSignature().getName() + " method");
        try {
            jp.proceed();
        } finally {

        }
        System.out.println("The method aroundAdvice() after invocation of the method: " + jp.getSignature().getName() + " method");
    }
}