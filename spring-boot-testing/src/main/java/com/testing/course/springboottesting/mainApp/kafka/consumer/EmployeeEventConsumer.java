package com.testing.course.springboottesting.mainApp.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.course.springboottesting.mainApp.exception.ShouldRetryNonBlockingException;
import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;


import java.net.SocketTimeoutException;

@Slf4j
@Component
//@KafkaListener(id = "multiGroup", topics = "employee-events", groupId = "main-group-id")
public class EmployeeEventConsumer {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;

    //@KafkaHandler
    @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 3000L, maxDelay = 6000L, multiplier = 2), timeout = "6000000")
    //@RetryableTopic(kafkaTemplate = "myRetryableTopicKafkaTemplate")
    @KafkaListener(topics = "employee-events")
    public void consumeMessage(String message) throws ShouldRetryNonBlockingException {
        log.info("Received message: {}", message);
        //simulate problem of processing message
        if(true) throw new ShouldRetryNonBlockingException();
        try {
            employeeService.saveEmployee(objectMapper.readValue(message, Employee.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @DltHandler
    public void processDltMessage(String message) {
        log.info("In DLQ");
        // ... message processing, persistence, etc
    }
}