package com.testing.course.springboottesting.mainApp.controller;


import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.model.EmployeeTechnology;
import com.testing.course.springboottesting.mainApp.service.EmployeeService;
import com.testing.course.springboottesting.mainApp.service.EmployeeTechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employeesTechnologies")
public class EmployeeTechnologyController {

    private EmployeeTechnologyService employeeTechnologyService;

    @Autowired
    EmployeeTechnologyController(EmployeeTechnologyService employeeTechnologyService){
        this.employeeTechnologyService = employeeTechnologyService;
    }


    @GetMapping("")
    public List<EmployeeTechnology> getAllEmployees (){
        return employeeTechnologyService.findAll();
    }

    @GetMapping("/yearsKnown/{years}")
    public List<EmployeeTechnology> getTechnologiesForEmployee(@PathVariable("years")long years) {return employeeTechnologyService.findTechnologiesKnownBy(years);}

}
