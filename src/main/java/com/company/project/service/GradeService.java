package com.company.project.service;

import java.util.List;

import com.company.project.core.service.BaseService;
import com.company.project.dao.GradeRepository;
import com.company.project.dao.SchoolRepository;
import com.company.project.domain.Grade;

import com.company.project.domain.Teacher;
import com.company.project.dto.GradeCreateDTO;
import com.company.project.dto.GradeUpdateDTO;
import com.company.project.mapper.CourseMapper;
import com.company.project.mapper.GradeMapper;
import com.company.project.vo.GradeSimpleVO;
import com.company.project.vo.GradeVO;
import com.company.project.vo.TeacherSimpleVO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GradeService extends BaseService<Grade, GradeVO, GradeCreateDTO, GradeUpdateDTO, Long> {
    private final GradeRepository repository;
    private final SchoolRepository schoolRepository;
    @Getter
    private final GradeMapper mapper;
    public GradeService(GradeRepository repository, SchoolRepository schoolRepository, GradeMapper mapper) {
        super(repository, Grade.class, mapper);
        this.repository = repository;
        this.schoolRepository = schoolRepository;
        this.mapper = mapper;
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

    @Override
    protected Long getIdFromUpdateDto(GradeUpdateDTO dto) {
        return dto.getId();
    }

    public List<GradeSimpleVO> findAllSimple() {
        List<Grade> grades = repository.findAll();
        return mapper.toSimpleVoList(grades);
    }
}

//        if (teacherIds != null && !teacherIds.isEmpty()) {
//            List<Teacher> selectedTeachers = schoolService.findAll().stream()
//                    .filter(teacher -> teacherIds.contains(teacher.getId()))
//                    .toList();
//            grade.setSchool(selectedTeachers);
//        }

