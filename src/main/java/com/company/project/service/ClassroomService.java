package com.company.project.service;


import java.util.List;

import com.company.project.core.service.BaseService;
import com.company.project.dao.ClassroomRepository;
import com.company.project.domain.Classroom;
import com.company.project.domain.Teacher;
import com.company.project.dto.ClassroomCreateDTO;
import com.company.project.dto.ClassroomUpdateDTO;
import com.company.project.dto.TeacherCreateDTO;
import com.company.project.dto.TeacherUpdateDTO;
import com.company.project.mapper.ClassroomMapper;
import com.company.project.mapper.TeacherMapper;
import com.company.project.vo.ClassroomDetailRecord;

import com.company.project.vo.ClassroomVO;
import com.company.project.vo.TeacherVO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClassroomService extends BaseService<Classroom, ClassroomVO, ClassroomCreateDTO, ClassroomUpdateDTO, Long> {

    private final ClassroomRepository repository;

    @Getter
    private final ClassroomMapper mapper;

    public ClassroomService(ClassroomRepository repository, ClassroomMapper mapper) {
        super(repository, Classroom.class, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClassroomDetailRecord> findAllWithNames() {
        return repository.findAllWithNames();
    }

    public List<ClassroomDetailRecord> findByGradeIdWithNames(Long gradeId) {
        return repository.findByGradeIdWithNames(gradeId);
    }

    @Override
    protected Long getIdFromUpdateDto(ClassroomUpdateDTO dto) {
        return dto.getId();
    }
}
