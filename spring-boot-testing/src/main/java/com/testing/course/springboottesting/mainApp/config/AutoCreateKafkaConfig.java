package com.testing.course.springboottesting.mainApp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Controller;

@Configuration
//@Profile("local")
public class AutoCreateKafkaConfig {
/*
    @Bean
    public NewTopic employeeEvents(){
        return TopicBuilder
                .name("employee-events")
                .replicas(1)
                .partitions(1)
                .build();
    }
 */
}
