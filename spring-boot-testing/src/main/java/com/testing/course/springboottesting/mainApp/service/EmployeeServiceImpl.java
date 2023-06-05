package com.testing.course.springboottesting.mainApp.service;

import com.testing.course.springboottesting.mainApp.exception.ResourceNotFoundException;
import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.repository.EmployeeTechnologyRepository;
import com.testing.course.springboottesting.mainApp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;
    private EmployeeTechnologyRepository employeeTechnologyRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeTechnologyRepository employeeKnownTechnologyRepository) {

        this.employeeRepository = employeeRepository;
        this.employeeTechnologyRepository = employeeKnownTechnologyRepository;
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> employeeDB = employeeRepository.findByEmail(employee.getEmail());
        if(employeeDB.isPresent()){
            throw new ResourceNotFoundException("Not saved. Employee already exist");
        }
        Employee savedEmployee = employeeRepository.save(employee);

        return savedEmployee;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
