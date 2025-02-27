package com.testing.course.springboottesting.mainApp.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.testing.course.springboottesting.mainApp.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
public class GenerallExceptionHandling {


    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> somethingBadHappen(NullPointerException ex){
        EmployeeErrorResponse error = new EmployeeErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Null pointer ex thrown you noob");
        error.setTimestamp(new Date(System.currentTimeMillis()));
        error.setUUID(BaseResponse.returnUUIDIfExist());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> timeoutHappen(TimeoutException ex){
        EmployeeErrorResponse error = new EmployeeErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Timeout, probably on putting message on kafka topic");
        error.setTimestamp(new Date(System.currentTimeMillis()));
        error.setUUID(BaseResponse.returnUUIDIfExist());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> parsingInputProblem(JsonProcessingException ex){
        EmployeeErrorResponse error = new EmployeeErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Problem with formatting input");
        error.setTimestamp(new Date(System.currentTimeMillis()));
        error.setUUID(BaseResponse.returnUUIDIfExist());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> somethingBadHappen(Exception ex){
        EmployeeErrorResponse error = new EmployeeErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Something really bad happen");
        error.setTimestamp(new Date(System.currentTimeMillis()));
        error.setUUID(BaseResponse.returnUUIDIfExist());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

}
