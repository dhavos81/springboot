package com.testing.course.springboottesting.beanLoading.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TennisCoach implements Coach{

    TennisCoach(){
        System.out.println("TennisCoach constructor");
    }
    @Override
    public String getDailyWorkout() {
        return "Strike ball with rocket";
    }

    @PostConstruct
    void postConstructMethod(){
        System.out.println("TennisCoach created");
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("TennisCoach destroyed");
    };
}
