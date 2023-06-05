package com.testing.course.springboottesting.mainApp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Aspect
@Component
public class CheckPerformanceApect {

    @Around(value = "execution(* com.testing..*(..))")
    public Object checkExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Begin in  around aspect");
        long start = new Date().getTime();
        Object resut;
        try{
            resut =  proceedingJoinPoint.proceed();
        }catch(Exception e){
            log.info("Something really bad happen"+e.toString());
            throw e;
        }finally{
            log.info(">>>>>"+proceedingJoinPoint.getSignature()+ " took " + (new Date().getTime()-start)+" milliseconds");
        }
        return resut;
    }

    @Before(value = "execution(* com.testing..*(..))")
    public void checkAspect(){
        log.info("Begin in aspect of before");
    }
}
