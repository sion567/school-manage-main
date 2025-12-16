package com.company.project.service;

import java.util.List;

import com.company.project.core.exception.ResourceNotFoundException;
import com.company.project.core.service.BaseService;
import com.company.project.dao.GradeRepository;
import com.company.project.dao.SchoolRepository;
import com.company.project.domain.Course;
import com.company.project.domain.Grade;
import com.company.project.domain.School;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GradeService extends BaseService<Grade, Long> {
    private final GradeRepository repository;
    private final SchoolRepository schoolRepository;
    public GradeService(GradeRepository repository, SchoolRepository schoolRepository, ModelMapper modelMapper) {
        super(repository, Grade.class, modelMapper);
        this.repository = repository;
        this.schoolRepository = schoolRepository;
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