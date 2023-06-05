package com.testing.course.springboottesting.customRepository.dao;


import com.testing.course.springboottesting.customRepository.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO{

    private EntityManager em;

    @Autowired
    StudentDAOImpl(EntityManager em){
        this.em=em;
    }

    @Override
    @Transactional
    public void save(Student student) {
        em.persist(student);
    }

    @Override
    public Student findById(int id) {
        return em.find(Student.class, id);
    }

    @Override
    public List<Student> findAll() {
        TypedQuery<Student> typedQuery = em.createQuery("FROM Student order by lastName", Student.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        TypedQuery<Student> typedQuery = em.createQuery("FROM Student where lastName=:lastName", Student.class);
        typedQuery.setParameter("lastName", lastName);
        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public Student update(Student student) {
       Student studentFromDb =  em.merge(student);
       return studentFromDb;
    }

    @Override
    @Transactional
    public int deleteById(int id) {
        //Student student =  em.find(Student.class, id);
        //em.remove(student);
        Query query = em.createQuery("DELETE FROM Student where id=:id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
