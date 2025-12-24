package com.company.project.service;

import java.util.List;
import java.util.Optional;

import com.company.project.core.service.BaseService;
import com.company.project.dao.SchoolRepository;
import com.company.project.dao.StudentRepository;
import com.company.project.dao.TeacherRepository;
import com.company.project.domain.School;
import com.company.project.dto.SchoolCreateDTO;
import com.company.project.dto.SchoolUpdateDTO;
import com.company.project.mapper.SchoolMapper;
import com.company.project.vo.SchoolSimpleVO;
import com.company.project.vo.SchoolStatistics;

import com.company.project.vo.SchoolVO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolService extends BaseService<School, SchoolVO, SchoolCreateDTO, SchoolUpdateDTO, Long> {
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    @Getter
    private final SchoolMapper mapper;

    public SchoolService(SchoolRepository schoolRepository,
                         TeacherRepository teacherRepository,
                         StudentRepository studentRepository, SchoolMapper mapper) {
        super(schoolRepository, School.class, mapper);
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    protected Long getIdFromUpdateDto(SchoolUpdateDTO dto) {
        return dto.getId();
    }


    public List<SchoolSimpleVO> findAllSimple() {
        List<School> schools = schoolRepository.findAll();
        return mapper.toSimpleVoList(schools);
    }

    // 确保在事务中加载懒加载集合
//    public UserDTO getUserWithOrdersEagerly(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        // 强制初始化集合（如果需要）
//        Hibernate.initialize(user.getOrders());
//
//        return modelMapper.map(user, UserDTO.class);
//    }


//    public void addTeacherToSchool(Long schoolId, Long studentId) {
//        School school = schoolRepository.findById(schoolId)
//                .orElseThrow(() -> new RuntimeException("School not found"));
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        school.addStudent(student);
//        schoolRepository.save(school);
//    }

//    public void addStudentToSchool(Long schoolId, Long studentId) {
//        School school = schoolRepository.findById(schoolId)
//                .orElseThrow(() -> new RuntimeException("School not found"));
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        school.addStudent(student);
//        schoolRepository.save(school);
//    }

    public Optional<School> findSchoolByName(String name) {
        return schoolRepository.findByName(name);
    }

//    public School getSchoolWithTeachers(Long schoolId) {
//        School school = schoolRepository.findById(schoolId)
//                .orElseThrow(() -> new RuntimeException("School not found"));
//
//        school.getTeachers().size(); // 触发懒加载
//        return school;
//    }

    public School getSchoolWithStudents(Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

        school.getStudents().size(); // 触发懒加载
        return school;
    }

    public long getTeacherCountBySubject(String subject) {
       // return teacherRepository.countBySubject(subject);
        return 0L;
    }

//    public long getStudentCountBySchool(Long schoolId) {
//        return studentRepository.countBySchoolId(schoolId);
//    }

    // 复杂业务逻辑
    @Transactional
    public SchoolStatistics getSchoolStatistics(Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

//        long totalTeachers = teacherRepository.countBySchoolId(schoolId);
//        long totalStudents = studentRepository.countBySchoolId(schoolId);
//        long totalCourses = teacherRepository.countDistinctSubjectsBySchoolId(schoolId);

        return new SchoolStatistics(school, 0, 0, 0);
    }


}
