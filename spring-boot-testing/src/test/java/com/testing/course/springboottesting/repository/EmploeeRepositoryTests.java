package com.testing.course.springboottesting.repository;

import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmploeeRepositoryTests {

    @Autowired
    EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
                employee = Employee.builder()
                .firstName("Pawel")
                .lastName("Bochinski")
                .email("dhavos@gmail.com").build();

    }

    //JUnit for save
    @Test
    @DisplayName("JUnit test for save employee")
    public void givenEmployeeObject_whenSave_thenReturnedSavedEmployee(){

        //given

        //when
        Employee savedEmployee = employeeRepository.save(employee);
        //then
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    @Test
    @DisplayName("JUnit test for get employees")
    public void givenEmployeesList_whenFindAll_thenEmployeessList(){

        //given
        Employee employeeSecond = Employee.builder()
                .firstName("Json")
                .lastName("Json")
                .email("json@gmail.com").build();

        employeeRepository.save(employee);
        employeeRepository.save(employeeSecond);
        //when
        List<Employee> employees = employeeRepository.findAll();
        //then
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test for get employee by id")
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){

        //given
        employeeRepository.save(employee);
        //when
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        //then
        Assertions.assertThat(employeeDB).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for get employee by email")
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject(){

        //given
        employeeRepository.save(employee);
        //when
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();
        //then
        Assertions.assertThat(employeeDB).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for get employee by first and last name with custom JPQL")
    public void givenFirstNameAndLastName_whenFindByFirstNameAndLastName_thenReturnEmployeeObject(){

        //given
        employeeRepository.save(employee);
        //when
        Optional<Employee> employeeDB = employeeRepository.findByFirstNameAndLastNameJPQL(employee.getFirstName(), employee.getLastName());
        //then
        Assertions.assertThat(employeeDB).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit test for get employee by first and last name with custom JPQL named params")
    public void givenFirstNameAndLastName_whenFindByFirstNameAndLastNameUsingNamedParams_thenReturnEmployeeObject(){

        //given
        employeeRepository.save(employee);
        //when
        Optional<Employee> employeeDB = employeeRepository.findByFirstNameAndLastNameNamedParamsJPQL(employee.getFirstName(), employee.getLastName());
        //then
        Assertions.assertThat(employeeDB).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit test for get employee by first and last name with custom JPQL native SQL")
    public void givenFirstNameAndLastName_whenFindByFirstNameAndLastNameUsingNativeSQL_thenReturnEmployeeObject(){

        //given
        employeeRepository.save(employee);
        //when
        Optional<Employee> employeeDB = employeeRepository.findByFirstNameAndLastNameNativeSQL(employee.getFirstName(), employee.getLastName());
        //then
        Assertions.assertThat(employeeDB).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit test for update employee")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject(){

        //given
        employeeRepository.save(employee);
        //when
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        employeeDB.setEmail("json@gmail.com");
        employeeDB.setFirstName("Json");
        Employee updatedEmployee = employeeRepository.save(employeeDB);
        //then
        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("json@gmail.com");
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Json");
    }

    @Test
    @DisplayName("JUnit test for delete employee")
    public void givenEmployeeObject_whenDeleteById_thenEmployeeObjectDeleted(){

        //given
        employeeRepository.save(employee);
        //when
        employeeRepository.delete(employee);
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        //then
        Assertions.assertThat(deletedEmployee).isEmpty();
    }

}
