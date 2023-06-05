package com.testing.course.springboottesting.mainApp.service;

import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.model.EmployeeTechnology;

import java.util.List;

public interface EmployeeTechnologyService {

    List<EmployeeTechnology> findAll();

    List<EmployeeTechnology> findTechnologiesKnownBy(long years);
}
