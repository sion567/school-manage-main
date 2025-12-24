package com.company.project.service;

import java.util.Optional;

import com.company.project.core.service.BaseService;
import com.company.project.dao.StudentRepository;
import com.company.project.domain.Student;

import com.company.project.dto.StudentCreateDTO;
import com.company.project.dto.StudentUpdateDTO;
import com.company.project.mapper.StudentMapper;
import com.company.project.mapper.TeacherMapper;
import com.company.project.vo.StudentVO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService extends BaseService<Student, StudentVO, StudentCreateDTO, StudentUpdateDTO, Long> {

    private final StudentRepository repository;
    @Getter
    private final StudentMapper mapper;
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        super(studentRepository, Student.class, studentMapper);
        this.repository = studentRepository;
        this.mapper = studentMapper;
    }

    public Optional<Student> findByStudentNumber(String no) {
        return repository.findByStudentNumber(no);
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    @Override
    protected Long getIdFromUpdateDto(StudentUpdateDTO dto) {
        return dto.getId();
    }
}
