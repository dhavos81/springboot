package com.testing.course.springboottesting.mainApp.repository;


import com.testing.course.springboottesting.mainApp.model.EmployeeTechnology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeTechnologyRepository extends JpaRepository<EmployeeTechnology,Long> {

    List<EmployeeTechnology> findByYearsKnownGreaterThan(Long yearsknown);
}
