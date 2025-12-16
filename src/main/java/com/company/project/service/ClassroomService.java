package com.company.project.service;


import java.util.List;

import com.company.project.core.service.BaseService;
import com.company.project.dao.ClassroomRepository;
import com.company.project.domain.Classroom;
import com.company.project.vo.ClassroomDetailRecord;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClassroomService extends BaseService<Classroom, Long> {
    private final ClassroomRepository repository;

    public ClassroomService(ClassroomRepository repository, ModelMapper modelMapper) {
        super(repository, Classroom.class, modelMapper);
        this.repository = repository;
    }

    public List<ClassroomDetailRecord> findAllWithNames() {
        return repository.findAllWithNames();
    }

    public List<ClassroomDetailRecord> findByGradeIdWithNames(Long gradeId) {
        return repository.findByGradeIdWithNames(gradeId);
    }
}
