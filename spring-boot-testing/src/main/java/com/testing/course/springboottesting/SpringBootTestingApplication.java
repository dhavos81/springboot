package com.testing.course.springboottesting;

import com.testing.course.springboottesting.mainApp.model.Employee;
import com.testing.course.springboottesting.mainApp.model.EmployeeTechnology;
import com.testing.course.springboottesting.mainApp.repository.EmployeeRepository;
import com.testing.course.springboottesting.mainApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringBootTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestingApplication.class, args);
	}

	@Bean
	@Autowired
	public CommandLineRunner commandLineRunner (String[] args, EmployeeService employeeService){
		return runner -> {
			Employee employee1 = Employee.builder()
					.firstName("Pawel")
					.lastName("Bochinski")
					.email("dhavos@gmail.com")
					.technologyDetail(List.of(
							EmployeeTechnology.builder()
									.technology("java")
									.yearsKnown(7)
									.build(),
							EmployeeTechnology.builder()
									.technology("spring")
									.yearsKnown(6)
									.build()))
					.build();
			employeeService.saveEmployee(employee1);

			Employee employee2 = Employee.builder()
					.firstName("John")
					.lastName("Kowalski")
					.email("kowal@gmail.com")
					.technologyDetail(List.of(
						EmployeeTechnology.builder()
						.technology("java")
						.yearsKnown(10)
						.build(),
						EmployeeTechnology.builder()
						.technology("hibernate")
						.yearsKnown(8)
						.build()))
					.build();
			employeeService.saveEmployee(employee2);
		};
	}

}
