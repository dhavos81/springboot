package com.testing.course.springboottesting.mainApp.service;

import com.testing.course.springboottesting.mainApp.model.EmployeeTechnology;
import com.testing.course.springboottesting.mainApp.repository.EmployeeTechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeTechnologyServiceImpl implements EmployeeTechnologyService{

    private EmployeeTechnologyRepository employeeTechnologyRepository;

    @Autowired
    public EmployeeTechnologyServiceImpl(EmployeeTechnologyRepository employeeKnownTechnologyRepository){
        this.employeeTechnologyRepository = employeeKnownTechnologyRepository;
    }

    @Override
    public List<EmployeeTechnology> findAll() {
        return employeeTechnologyRepository.findAll();
    }

    @Override
    public List<EmployeeTechnology> findTechnologiesKnownBy(long years) {
        return employeeTechnologyRepository.findByYearsKnownGreaterThan(years);
    }
}
