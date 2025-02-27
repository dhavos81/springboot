package com.testing.course.springboottesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.course.springboottesting.main.BaseTest;
import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITest implements BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeRepository employeeRepository;

    RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    Employee employee = Employee.builder()
            .firstName("Pawel")
            .lastName("Bochinski")
            .email("dhavos@gmail.com").build();

    @BeforeEach
    void setup(){
        restTemplate = new RestTemplate();
        log.info("application started on port:{}", port);
        log.info("Initializing DB..");
        jdbcTemplate.execute("delete from EMPLOYEES_TECHNOLOGY");
        jdbcTemplate.execute("delete from employees");
        //jdbcTemplate.execute("insert into employees(first_name, last_name, email) values('Jay', 'Bochinski', 'jon@gmail.com')");

    }

    @Test
    @DisplayName("JUnit test for create employee")
    public void givenEmployee_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given

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
    @DisplayName("JUnit test for get all employees")
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {

        //given
        employeeRepository.save(employee);

        //when
        ResultActions response = mockMvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeList.size()", CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("JUnit test for get employee by id (positive scenario)")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        //given

        Employee employeeFromDB = employeeRepository.save(employee);
        //when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeFromDB.getId())
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Pawel")));
    }

    @Test
    @DisplayName("JUnit test for get employee by id (negative scenerio)")
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        //given

        //when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", -1)
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
        Employee employeeToUpdate = Employee.builder()
                .firstName("Json")
                .lastName("Bochinski")
                .email("json@gmail.com").build();

        Employee employeeFromDB = employeeRepository.save(employee);

        //when
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeFromDB.getId())
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

        //when
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", -1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        //then
        response.andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("JUnit test for delete employee")
    public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccessMessage() throws Exception {

        //given
        Employee employeeFromDB = employeeRepository.save(employee);

        //when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeFromDB.getId())
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andDo(print())
                .andExpect(status().isOk());

    }

}
