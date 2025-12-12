package com.company.project.service;

import java.util.Optional;

import com.company.project.core.service.BaseService;
import com.company.project.dao.StudentRepository;
import com.company.project.domain.Student;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService extends BaseService<Student, Long> {

    private final StudentRepository repository;

    public StudentService(StudentRepository studentRepository) {
        super(studentRepository);
        this.repository = studentRepository;
    }

    public Optional<Student> findByStudentNumber(String no) {
        return repository.findByStudentNumber(no);
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }
}
