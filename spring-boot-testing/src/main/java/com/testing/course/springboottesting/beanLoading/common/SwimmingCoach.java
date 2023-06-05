package com.testing.course.springboottesting.beanLoading.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

//this might be external pojo so we cant add Bean to it.
public class SwimmingCoach implements Coach{
    @Override
    public String getDailyWorkout() {
        return "Do some diving";
    }

    @PostConstruct
    void postConstructMethod(){
        System.out.println("SwimmingCoach created");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("SwimmingCoach destroyed");
    };
}
