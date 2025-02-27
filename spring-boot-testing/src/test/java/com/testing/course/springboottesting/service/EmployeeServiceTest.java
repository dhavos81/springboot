package com.testing.course.springboottesting.service;


import com.testing.course.springboottesting.main.BaseTest;
import com.testing.course.springboottesting.mainApp.exception.ResourceNotFoundException;
import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.repository.EmployeeRepository;
import com.testing.course.springboottesting.mainApp.service.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest implements BaseTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);

        employee = Employee.builder()
                .firstName("Pawel")
                .lastName("Bochinski")
                .email("dhavos@gmail.com").build();
        }


    @Test
    @DisplayName("JUnit test for save employee service")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        //given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        Employee employeeDB = employeeService.saveEmployee(employee);

        //then
        Assertions.assertThat(employeeDB).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for save employee that is in DB service")
    public void givenEmployeeObject_whenSaveEmployee_thenThrowException(){
        //given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        //given(employeeRepository.save(employee)).willReturn(employee);

        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        //then
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    @Test
    @DisplayName("JUnit test for get all employees that are in DB, service")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnListEmployees(){
        //given
        Employee employeeSecond = Employee.builder()
                .firstName("Pawel")
                .lastName("Bochinski")
                .email("dhavos@gmail.com").build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employeeSecond));

        //when
        List<Employee> listEmployees = employeeService.findAll();

        //then
        Assertions.assertThat(listEmployees).isNotNull();
        Assertions.assertThat(listEmployees.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test for get all employees that are in DB, alternative flow, service")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmptyListEmployees(){
        //given

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Employee> listEmployees = employeeService.findAll();

        //then
        Assertions.assertThat(listEmployees).isNotNull();
        Assertions.assertThat(listEmployees).isEmpty();
        Assertions.assertThat(listEmployees.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("JUnit test for get employee by id that are in DB, service")
    public void givenEmployee_whenGetEmployeeById_thenReturnEmployee(){
        //given

        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when
        Optional<Employee> employee = employeeService.findById(1);

     //   employee.ifPresent(employee1 -> employee1.setEmail("cos"));
     //   if(employee.isPresent()) employee.get().setEmail("cos");

        //then
        Assertions.assertThat(employee.isPresent()).isTrue();
    }

    @Test
    @DisplayName("JUnit test for update employee that are in DB, service")
    public void givenEmployee_whenUpdateEmployee_thenReturnEmployee(){
        //given

        given(employeeRepository.save(any(Employee.class))).willAnswer((invocation)-> invocation.getArgument(0));
        employee.setFirstName("Json");

        //when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Json");
    }

    @Test
    @DisplayName("JUnit test for delete employee that are in DB, service")
    public void givenEmployee_whenDeleteEmployee_thenNothing(){

        Long employeeId = 1L;
        //given
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when
        employeeService.deleteEmployee(employeeId);

        //then
        verify(employeeRepository, times(1)).deleteById(any(Long.class));
    }

}
