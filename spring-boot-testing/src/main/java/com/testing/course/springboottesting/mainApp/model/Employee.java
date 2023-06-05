package com.testing.course.springboottesting.mainApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<EmployeeTechnology> technologyDetail= new ArrayList<>();

    public void addTechnology(EmployeeTechnology employeeTechnology){
        technologyDetail.add(employeeTechnology);
        //employeeTechnology.setEmployee(this);
    }

    public void removeTechnology(EmployeeTechnology employeeTechnology){
        technologyDetail.remove(employeeTechnology);
        //employeeTechnology.setEmployee(null);
    }
}
