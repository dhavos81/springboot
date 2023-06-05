package com.testing.course.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Pawel")
                .lastName("Bochinski")
                .email("dhavos@gmail.com").build();

    }

    @Test
    @DisplayName("JUnit test for create employee")
    public void givenEmployee_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given
        given(employeeService.saveEmployee(any(Employee.class))).willAnswer((invocation)-> invocation.getArgument(0));
        //given(employeeService.saveEmployee(any(Employee.class))).willReturn(employee);

        //when
        ResultActions response = mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        //then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    @Test
    @DisplayName("JUnit test for get all employees")
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {

        //given
        List<Employee> employeeList  = List.of(
                employee,
                Employee.builder().firstName("Json").lastName("Json").email("json@gmail.com").build()
        );
        given(employeeService.findAll()).willReturn(employeeList);

        //when
        ResultActions response = mockMvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(employeeList.size())));
    }

    @Test
    @DisplayName("JUnit test for get employee by id (positive scenario)")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        //given
        long employeeId = 1L;
        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));

        //when
        ResultActions response = mockMvc.perform(get("/api/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));
    }

    @Test
    @DisplayName("JUnit test for get employee by id (negative scenerio)")
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        //given
        long employeeId = 1L;
        given(employeeService.findById(employeeId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("JUnit test for update employee (positive scenerio)")
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {

        //given
        long employeeId = 1L;
         Employee employeeToUpdate = Employee.builder()
                 .firstName("Json")
                 .lastName("Bochinski")
                 .email("json@gmail.com").build();


        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer((invocation)-> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeToUpdate))
        );

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employeeToUpdate.getFirstName())));

    }

    @Test
    @DisplayName("JUnit test for update employee (negative scenerio)")
    public void givenEmployee_whenEmployeeNotFound_thenReturnNotFoundSTatusCode() throws Exception {

        //given
        long employeeId = 1L;

        given(employeeService.findById(employeeId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(put("/api/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        //then
        response.andDo(print())
                .andExpect(status().isNotFound());

        verify(employeeService, times(0)).updateEmployee(any(Employee.class));

    }

    @Test
    @DisplayName("JUnit test for delete employee")
    public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccessMessage() throws Exception {

        //given
        long employeeId = 1L;

        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(print())
                .andExpect(status().isOk());

    }
}
