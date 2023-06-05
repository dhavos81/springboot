package com.testing.course.springboottesting.mainApp.model;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name="employeesTechnology")
public class EmployeeTechnology {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="technology")
    private String technology;

    @Column(name="yearsKnown")
    private Integer yearsKnown;

}
