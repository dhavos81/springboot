package com.testing.course.springboottesting.mainApp.mapper;

import com.testing.course.springboottesting.mainApp.dto.EmployeeDTO;
import com.testing.course.springboottesting.mainApp.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO entityToDTO(Employee source);
    Employee dTOToEntity(EmployeeDTO destination);

}
