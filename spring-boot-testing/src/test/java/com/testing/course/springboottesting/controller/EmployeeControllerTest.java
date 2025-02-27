package com.testing.course.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.course.springboottesting.main.BaseTest;
import com.testing.course.springboottesting.mainApp.controller.EmployeeController;
import com.testing.course.springboottesting.mainApp.mapper.EmployeeMapper;
import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
//import static org.mockito.BDDMockito.when;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"com.testing.course.springboottesting.mainApp.mapper"}) //for mapper
public class EmployeeControllerTest implements BaseTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @Spy
    EmployeeMapper employeeMapper;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Pawel")
                .lastName("Bochinski")
                .email("dhavos@gmail.com").build();

    }

    @Test
    @Order(value = 1)
    @DisplayName("JUnit test for create employee")
    public void givenEmployee_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given
        given(employeeService.saveEmployee(any(Employee.class))).willAnswer((invocation)-> invocation.getArgument(0));
        //given(employeeService.saveEmployee(any(Employee.class))).willReturn(employee);

        //when
        ResultActions response = mockMvc.perform(post("/api/employees")
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
    @Order(value = 6)
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
                .andExpect(jsonPath("$.employeeList.size()", CoreMatchers.is(employeeList.size())))
                .andExpect(jsonPath("$.employeeList.[0].firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.employeeList.[1].firstName", CoreMatchers.is(employeeList.get(1).getFirstName())));
    }

    @Test
    @Order(value = 4)
    @DisplayName("JUnit test for get employee by id (positive scenario)")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        //given
        long employeeId = 1L;
        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));

        //when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));
    }

    @Test
    @Order(value = 5)
    @DisplayName("JUnit test for get employee by id (negative scenerio)")
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        //given
        long employeeId = 1L;
        given(employeeService.findById(employeeId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @Order(value = 2)
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
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeToUpdate))
        );

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employeeToUpdate.getFirstName())));

    }

    @Test
    @Order(value = 3)
    @DisplayName("JUnit test for update employee (negative scenerio)")
    public void givenEmployee_whenEmployeeNotFound_thenReturnNotFoundSTatusCode() throws Exception {

        //given
        long employeeId = 1L;

        given(employeeService.findById(employeeId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        //then
        response.andDo(print())
                .andExpect(status().isNotFound());

        verify(employeeService, times(0)).updateEmployee(any(Employee.class));

    }

    @Test
    @Order(value = 7)
    @DisplayName("JUnit test for delete employee")
    public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccessMessage() throws Exception {

        //given
        long employeeId = 1L;

        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));
        //doNothing().when(employeeService).deleteEmployee(employeeId);
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(print())
                .andExpect(status().isOk());

    }
}
