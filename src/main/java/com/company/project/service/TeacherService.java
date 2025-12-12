package com.company.project.service;

import java.util.List;
import java.util.Optional;

import com.company.project.core.exception.BusinessException;
import com.company.project.core.service.BaseService;
import com.company.project.dao.StudentRepository;
import com.company.project.dao.TeacherRepository;
import com.company.project.domain.Teacher;
import com.company.project.domain.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherService extends BaseService<Teacher, Long> {

    private final TeacherRepository repository;
    private final UserAccountService userAccountService;

    private final StudentRepository studentRepository;

    public TeacherService(TeacherRepository repository,
                          StudentRepository studentRepository,
                          UserAccountService userAccountService) {
        super(repository);
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.userAccountService = userAccountService;
    }

    public Optional<Teacher> findTeacherByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Teacher createTeacher(Teacher teacher) {
        Teacher t =  repository.save(teacher);
        userAccountService.createUserAccount(t);
        return t;
    }

    public List<Teacher> findBySchoolId(Long schoolId) {
        return repository.findBySchoolId(schoolId);
    }

    public List<Teacher> searchTeachers(String keyword) {
        return repository.findByFirstNameContainingOrLastNameContaining(keyword, keyword);
    }

//    public void assignStudentToTeacher(Long teacherId, Long studentId) {
//        Teacher teacher = teacherRepository.findById(teacherId)
//                .orElseThrow(() -> new RuntimeException("Teacher not found"));
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        teacher.addStudent(student);
//        teacherRepository.save(teacher);
//    }
    public List<Teacher> findAllWithSchool() {
        return repository.findAllWithSchool();
    }

    public List<Teacher> findBySchoolIdWithSchool(Long schoolId) {
        return repository.findBySchoolIdWithSchool(schoolId);
    }
}
