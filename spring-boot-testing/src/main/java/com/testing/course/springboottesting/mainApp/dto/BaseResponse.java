package com.testing.course.springboottesting.mainApp.dto;

import com.testing.course.springboottesting.mainApp.filter.AddUUIDFilter;
import com.testing.course.springboottesting.mainApp.model.Employee;
import lombok.*;
import org.slf4j.MDC;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseResponse {
    protected UUID uuid;
    protected List<EmployeeDTO> employeeList;

    public BaseResponse(List<EmployeeDTO> employeeList){
        this.employeeList=employeeList;
        this.uuid = returnUUIDIfExist();
    }

    public static UUID returnUUIDIfExist(){
        if (MDC.get(AddUUIDFilter.UUID_KEY)!= null) {
            return UUID.fromString(MDC.get(AddUUIDFilter.UUID_KEY));
        }
        else{
            return null;
        }
    }

}
