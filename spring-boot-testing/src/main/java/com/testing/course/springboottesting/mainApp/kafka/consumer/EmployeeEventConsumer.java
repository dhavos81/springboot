package com.testing.course.springboottesting.mainApp.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeEventConsumer {

    @KafkaListener(topics = "employee-events", groupId = "main-group-id")
    public void consumeMessage(String message) {
        log.info("Received message: {}", message);
    }
}