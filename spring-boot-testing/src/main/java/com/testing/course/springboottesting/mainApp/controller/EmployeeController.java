package com.testing.course.springboottesting.mainApp.controller;


import com.testing.course.springboottesting.mainApp.dto.BaseResponse;
import com.testing.course.springboottesting.mainApp.dto.EmployeeDTO;
import com.testing.course.springboottesting.mainApp.mapper.EmployeeMapper;
import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.rest.EmployeeNotFoundException;
import com.testing.course.springboottesting.mainApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Employee createEmployee(@RequestBody Employee employee){
        return  employeeService.saveEmployee(employee);
    }

    @GetMapping("")
    public BaseResponse getAllEmployees (){
        List<Employee> employeeList = employeeService.findAll();
        List<EmployeeDTO> employeeDTOList = employeeList.stream()
                .map(employee -> {
                    return employeeMapper.entityToDTO(employee);
                })
                .collect(Collectors.toList());
        return new BaseResponse(employeeList);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId")long employeeId){
        return employeeService.findById(employeeId)
                .map(ResponseEntity::ok)
                .orElseThrow(()->new EmployeeNotFoundException("Employee not found"));
    }

    @PutMapping("/simple")
    public ResponseEntity<Employee> updateEmployeeSipmple(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.updateEmployee(employee),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId, @RequestBody Employee employee) {
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employeeDB = optionalEmployee.get();
            employeeDB.setFirstName(employee.getFirstName());
            employeeDB.setLastName(employee.getLastName());
            employeeDB.setEmail(employee.getEmail());
            employeeService.updateEmployee(employeeDB);
            return new ResponseEntity<>(employeeDB, HttpStatus.OK);
        } else if (true) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return employeeService.findById(employeeId)
                .map(savedEmployee -> {
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());
                    Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId){
        return employeeService.findById(employeeId)
                .map(foundEmployee -> {
                    employeeService.deleteEmployee(foundEmployee.getId());
                    return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/runtimeEx")
    public ResponseEntity<String> throwRuntimeEx (){
        throw new RuntimeException();
    }

    @GetMapping("/secured/employees")
    public BaseResponse getAllEmployeesSecured (){
        return getAllEmployees();
    }
}
