package com.testing.course.springboottesting.mainApp.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.testing.course.springboottesting.mainApp.domain.AddEmployeeEvent;
import com.testing.course.springboottesting.mainApp.kafka.producer.EmployeeEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/kafka/employees")
public class KafkaEmployeeController {

    @Autowired
    EmployeeEventProducer employeeEventProducer;

    @PostMapping
    public ResponseEntity<AddEmployeeEvent> addEmployee (@RequestBody AddEmployeeEvent addEmployeeEvent) throws ExecutionException, JsonProcessingException, InterruptedException, TimeoutException {

        //employeeEventProducer.sendAddEmployeeEvent(addEmployeeEvent);
        employeeEventProducer.sendAddEmployeeEventSynch(addEmployeeEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(addEmployeeEvent);
    }
}
