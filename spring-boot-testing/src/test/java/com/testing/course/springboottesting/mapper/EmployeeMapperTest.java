package com.testing.course.springboottesting.mapper;




import com.testing.course.springboottesting.mainApp.dto.EmployeeDTO;
import com.testing.course.springboottesting.mainApp.mapper.EmployeeMapper;
import com.testing.course.springboottesting.mainApp.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeMapperTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void givenEmplyeeDTO_whenMaps_thenCorrect(){
        //given
        EmployeeDTO employeeDTO;
        employeeDTO = EmployeeDTO.builder()
                .firstName("Pawel")
                .lastName("Bochinski")
                .email("dhavos@gmail.com").build();
        //when
        Employee employee = employeeMapper.dTOToEntity(employeeDTO);
        //then
        assertEquals(employeeDTO.getFirstName(), employee.getFirstName());
        assertEquals(employeeDTO.getLastName(), employee.getLastName());
        assertEquals(employeeDTO.getEmail(), employee.getEmail());
    }

    @Test
    public void givenEmplyee_whenMaps_thenCorrect(){
        //given
        Employee employee;
        employee = Employee.builder()
                .firstName("Pawel")
                .lastName("Bochinski")
                .email("dhavos@gmail.com").build();
        //when
        EmployeeDTO employeeDTO = employeeMapper.entityToDTO(employee);
        //then
        assertEquals(employee.getFirstName(), employeeDTO.getFirstName());
        assertEquals(employee.getLastName(), employeeDTO.getLastName());
        assertEquals(employee.getEmail(), employeeDTO.getEmail());
    }
}
