package com.testing.course.springboottesting.mainApp.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.course.springboottesting.mainApp.domain.AddEmployeeEvent;
import com.testing.course.springboottesting.mainApp.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Slf4j
@Component
public class EmployeeEventProducer {

    @Autowired
    KafkaTemplate<Long, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendAddEmployeeEvent(AddEmployeeEvent addEmployeeEvent) throws JsonProcessingException {
        String jsonRepresentation = objectMapper.writeValueAsString(addEmployeeEvent.getEmployeeDTO());
        CompletableFuture<SendResult<Long,String>> result =  kafkaTemplate.sendDefault(addEmployeeEvent.getAddEmployeeEventId(), jsonRepresentation);
        result.whenComplete((msg,ex)->{
            if (ex != null) {
                log.info("Problem sending employee with key: {}", addEmployeeEvent.getAddEmployeeEventId());
                log.info("Exeption while sending to employee kafka topic", ex);
            } else {
                log.info("Employee event pushed to employee kafka topic, key: {} and partition: {}",addEmployeeEvent.getAddEmployeeEventId(), msg.getRecordMetadata().partition());
            }
        });
    }

    public void sendAddEmployeeEventSynch(Long id, Employee employee) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String jsonRepresentation = objectMapper.writeValueAsString(employee);
        SendResult<Long,String> result;
        try{
            //result =  kafkaTemplate.sendDefault(id, jsonRepresentation).get(2, TimeUnit.SECONDS);
            result = kafkaTemplate.send("employee-events", jsonRepresentation).get(2, TimeUnit.SECONDS);

        }catch(Exception ex){
            log.error("Error sending employee to kafka synch", ex);
            throw ex;
        }
        log.info("Added employee to topic synch {}: ", result.toString());
    }
}
