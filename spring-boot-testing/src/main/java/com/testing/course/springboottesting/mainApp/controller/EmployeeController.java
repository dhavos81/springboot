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

import java.util.ArrayList;
import java.util.Arrays;
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
    public ResponseEntity<BaseResponse> getAllEmployees (){
        List<Employee> employeeList = employeeService.findAll();
        List<EmployeeDTO> employeeDTOList = employeeList.stream()
                .map(employee -> {
                    return employeeMapper.entityToDTO(employee);
                })
                .collect(Collectors.toList());
        return (new ResponseEntity<>(new BaseResponse(employeeDTOList),HttpStatus.OK));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<BaseResponse> getEmployeeById(@PathVariable("employeeId")long employeeId){
        Optional<Employee> employeeOptional = employeeService.findById(employeeId);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            return new ResponseEntity<>(new BaseResponse(new ArrayList<EmployeeDTO>(Arrays.asList(employeeMapper.entityToDTO(employee)))),HttpStatus.OK);
        }else{
            throw new EmployeeNotFoundException("Employee not found");
        }
        /*
        return employeeService.findById(employeeId)
                .map(ResponseEntity::ok)
                .orElseThrow(()->new EmployeeNotFoundException("Employee not found")); */
    }

    @PutMapping("/simple")
    public ResponseEntity<BaseResponse> updateEmployeeSipmple(@RequestBody EmployeeDTO employeeDTO){
        Employee employee = employeeMapper.dTOToEntity(employeeDTO);
        employee = employeeService.updateEmployee(employee);
        employeeDTO = employeeMapper.entityToDTO(employee);
        return new ResponseEntity<>(new BaseResponse(new ArrayList<EmployeeDTO>(Arrays.asList(employeeDTO))),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateEmployee(@PathVariable("id") long employeeId, @RequestBody EmployeeDTO employeeDTO) {
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = employeeMapper.dTOToEntity(employeeDTO);
            Employee employeeDB = optionalEmployee.get();
            employeeDB.setFirstName(employee.getFirstName());
            employeeDB.setLastName(employee.getLastName());
            employeeDB.setEmail(employee.getEmail());
            employee = employeeService.updateEmployee(employeeDB);
            employeeDTO = employeeMapper.entityToDTO(employee);
            return new ResponseEntity<>(new BaseResponse(new ArrayList<EmployeeDTO>(Arrays.asList(employeeDTO))),HttpStatus.OK);
        } else {
            throw new EmployeeNotFoundException("Employee not found");
            //return new ResponseEntity(new BaseResponse(null), HttpStatus.NOT_FOUND);
        }
        /*
        return employeeService.findById(employeeId)
                .map(savedEmployee -> {
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());
                    Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND)); */
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId){
        return employeeService.findById(employeeId)
                .map(foundEmployee -> {
                    employeeService.deleteEmployee(foundEmployee.getId());
                    return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
                })
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @GetMapping("/runtimeEx")
    public ResponseEntity<String> throwRuntimeEx (){
        throw new RuntimeException();
    }

    @GetMapping("/secured/employees")
    public ResponseEntity<BaseResponse> getAllEmployeesSecured (){
        return getAllEmployees();
    }
}
