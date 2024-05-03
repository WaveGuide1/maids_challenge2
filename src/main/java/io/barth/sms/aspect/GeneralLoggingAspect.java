package io.barth.sms.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class GeneralLoggingAspect {

    @Pointcut("execution(* io.barth.sms.product.*.*(..)) || " +
            "execution(* io.barth.sms.client.*.*(..)) || " +
            "execution(* io.barth.sms.authentication.*.*(..)) || " +
            "execution(* io.barth.sms.order.*.*(..))")
    public void loggingPointcut(){}

    /*

    @Before("loggingPointcut()")
    public void before(JoinPoint joinPoint){
        log.info("Before Method is Invoked" + joinPoint.getSignature() + " " + joinPoint.getKind());
    }

    @After("loggingPointcut()")
    public void after(JoinPoint joinPoint){
        log.info("After Method is Invoked" + joinPoint.getSignature() + " " + joinPoint.getKind());
    }
    */

    @Around("loggingPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Before Method is Invoked{} {}", joinPoint.getSignature(), joinPoint.getKind());
        Object object = joinPoint.proceed();

        log.info("After Method is Invoked{} {}", joinPoint.getSignature(), joinPoint.getKind());

        return object;
    }
}
