package com.company.project.service;

import java.util.List;
import java.util.Optional;

import com.company.project.core.exception.BusinessException;
import com.company.project.core.service.BaseService;
import com.company.project.dao.StudentRepository;
import com.company.project.dao.TeacherRepository;
import com.company.project.domain.School;
import com.company.project.domain.Student;
import com.company.project.domain.Teacher;
import com.company.project.domain.User;

import com.company.project.dto.SchoolCreateDTO;
import com.company.project.dto.SchoolUpdateDTO;
import com.company.project.dto.TeacherCreateDTO;
import com.company.project.dto.TeacherUpdateDTO;
import com.company.project.mapper.SchoolMapper;
import com.company.project.mapper.TeacherMapper;
import com.company.project.vo.SchoolSimpleVO;
import com.company.project.vo.SchoolVO;
import com.company.project.vo.TeacherSimpleVO;
import com.company.project.vo.TeacherVO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherService extends BaseService<Teacher, TeacherVO, TeacherCreateDTO, TeacherUpdateDTO, Long> {

    private final TeacherRepository repository;
    private final UserAccountService userAccountService;

    private final StudentRepository studentRepository;

    @Getter
    private final TeacherMapper mapper;

    public TeacherService(TeacherRepository repository,
                          StudentRepository studentRepository,
                          UserAccountService userAccountService,
                          TeacherMapper mapper) {
        super(repository, Teacher.class, mapper);
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.userAccountService = userAccountService;
        this.mapper = mapper;
    }

    @Override
    protected Long getIdFromUpdateDto(TeacherUpdateDTO dto) {
        return dto.getId();
    }

    public Optional<Teacher> findTeacherByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Teacher> findBySchoolId(Long schoolId) {
        return repository.findBySchoolId(schoolId);
    }

    public List<TeacherVO> searchTeachers(String keyword) {
        List<Teacher> teachers = repository.findByFirstNameContainingOrLastNameContaining(keyword, keyword);
        return mapper.toVOList(teachers);
    }

    public List<TeacherSimpleVO> findAllSimple() {
        List<Teacher> teachers = repository.findAll();
        return mapper.toSimpleVOList(teachers);
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
