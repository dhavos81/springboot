package com.testing.course.springboottesting.customRepository.dao;



import com.testing.course.springboottesting.customRepository.entity.Student;

import java.util.List;

public interface StudentDAO {

    public void save(Student student);

    public Student findById(int id);

    public List<Student> findAll();

    public List<Student> findByLastName(String lastName);

    public Student update(Student student);

    public int deleteById(int id);

}
