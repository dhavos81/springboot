package com.testing.course.springboottesting.mainApp.domain;

import com.testing.course.springboottesting.mainApp.dto.EmployeeDTO;
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
    private EmployeeDTO employeeDTO;
}
