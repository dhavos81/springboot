package com.testing.course.springboottesting.beanLoading.config;


import com.testing.course.springboottesting.beanLoading.common.Coach;
import com.testing.course.springboottesting.beanLoading.common.SwimmingCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {

    @Bean
    Coach swimmingCoach(){
        return new SwimmingCoach();
    }
}
