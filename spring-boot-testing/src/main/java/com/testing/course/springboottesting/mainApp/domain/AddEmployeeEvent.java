package com.testing.course.springboottesting.mainApp.domain;

import com.testing.course.springboottesting.mainApp.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AddEmployeeEvent {

    private Long addEmployeeEventId;
    private Employee employee;
}
