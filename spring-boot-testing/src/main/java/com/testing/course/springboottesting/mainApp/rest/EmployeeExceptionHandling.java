package com.testing.course.springboottesting.mainApp.rest;

import com.testing.course.springboottesting.mainApp.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class EmployeeExceptionHandling {

    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> employeeNotFound(EmployeeNotFoundException ex){
        EmployeeErrorResponse error = new EmployeeErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(new Date(System.currentTimeMillis()));
        error.setUUID(BaseResponse.returnUUIDIfExist());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
