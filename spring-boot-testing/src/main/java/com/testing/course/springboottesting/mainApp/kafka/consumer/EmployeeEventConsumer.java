package com.testing.course.springboottesting.mainApp.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeEventConsumer {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;

    @Retryable
    @KafkaListener(topics = "employee-events", groupId = "main-group-id")
    public void consumeMessage(String message) {
        log.info("Received message: {}", message);
        //simulate problem of processing message
        if(true) throw new RuntimeException();
        try {
            employeeService.saveEmployee(objectMapper.readValue(message, Employee.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}