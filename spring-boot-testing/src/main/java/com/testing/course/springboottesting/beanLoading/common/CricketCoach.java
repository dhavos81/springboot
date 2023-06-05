package com.testing.course.springboottesting.beanLoading.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CricketCoach implements Coach{

    CricketCoach(){
        System.out.println("CricketCoach constructor");
    }
    @Override
    public String getDailyWorkout() {
        return "Do some swings!";
    }

    @PostConstruct
    void postConstructMethod(){
        System.out.println("CrickietCoach created");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("CryckietCoach destroyed");
    };
}
