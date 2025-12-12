package com.company.project.service;

import java.util.List;

import com.company.project.core.exception.ResourceNotFoundException;
import com.company.project.core.service.BaseService;
import com.company.project.dao.GradeRepository;
import com.company.project.dao.SchoolRepository;
import com.company.project.domain.Grade;
import com.company.project.domain.School;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GradeService extends BaseService<Grade, Long> {
    private final GradeRepository repository;
    private final SchoolRepository schoolRepository;
    public GradeService(GradeRepository repository, SchoolRepository schoolRepository) {
        super(repository);
        this.repository = repository;
        this.schoolRepository = schoolRepository;
    }

    public Grade createGrade(Grade grade) {
//        School school = schoolRepository.findById(grade.getSchool().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("School Id:" + grade.getSchool().getId() +". not found"));
//        grade.setSchool(school);
        return repository.save(grade);
    }

    public List<Grade> findBySchoolId(Long schoolId) {
        return repository.findBySchoolId(schoolId);
    }

    public List<Grade> findAllWithSchool() {
        return repository.findAllWithSchool();
    }

    public List<Grade> findBySchoolIdWithSchool(Long schoolId) {
        return repository.findBySchoolIdWithSchool(schoolId);
    }
}

//        if (teacherIds != null && !teacherIds.isEmpty()) {
//            List<Teacher> selectedTeachers = schoolService.findAll().stream()
//                    .filter(teacher -> teacherIds.contains(teacher.getId()))
//                    .toList();
//            grade.setSchool(selectedTeachers);
//        }