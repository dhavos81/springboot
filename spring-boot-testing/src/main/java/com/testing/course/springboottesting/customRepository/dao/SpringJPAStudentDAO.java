package com.testing.course.springboottesting.customRepository.dao;



import com.testing.course.springboottesting.customRepository.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(path="members")
public interface SpringJPAStudentDAO extends JpaRepository<Student, Integer> {
}
