package com.company.project.service;

import java.util.Optional;

import com.company.project.core.service.BaseService;
import com.company.project.dao.SchoolRepository;
import com.company.project.dao.StudentRepository;
import com.company.project.dao.TeacherRepository;
import com.company.project.domain.School;
import com.company.project.vo.SchoolStatistics;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolService extends BaseService<School, Long> {
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public SchoolService(SchoolRepository schoolRepository,
                         TeacherRepository teacherRepository,
                         StudentRepository studentRepository) {
        super(schoolRepository);
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public School createSchool(School school) {
        return schoolRepository.save(school);
    }

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
