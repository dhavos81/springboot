package com.testing.course.springboottesting.mainApp.service;

import com.testing.course.springboottesting.mainApp.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(long id);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(long id);

}
