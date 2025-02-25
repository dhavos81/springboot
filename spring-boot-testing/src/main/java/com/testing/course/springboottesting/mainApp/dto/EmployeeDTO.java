package com.testing.course.springboottesting.mainApp.dto;

import com.testing.course.springboottesting.mainApp.model.EmployeeTechnology;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeDTO {

    private long id;

    private String email;

    private String firstName;

    private String lastName;

    private List<EmployeeTechnology> technologyDetail= new ArrayList<>();

}
