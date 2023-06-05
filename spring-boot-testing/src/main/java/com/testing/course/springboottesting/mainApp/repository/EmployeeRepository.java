package com.testing.course.springboottesting.mainApp.repository;


import com.testing.course.springboottesting.mainApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Optional<Employee> findByFirstNameAndLastNameJPQL(String firstName, String lastName);

    @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    Optional<Employee> findByFirstNameAndLastNameNamedParamsJPQL(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2", nativeQuery = true)
    Optional<Employee> findByFirstNameAndLastNameNativeSQL(String firstName, String lastName);

}
