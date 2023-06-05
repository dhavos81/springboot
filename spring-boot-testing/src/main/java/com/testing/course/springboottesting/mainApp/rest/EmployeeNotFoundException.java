package com.testing.course.springboottesting.mainApp.rest;

import java.util.function.Supplier;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) { super(message); }
}
