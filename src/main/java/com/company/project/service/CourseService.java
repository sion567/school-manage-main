package com.company.project.service;

import com.company.project.core.service.BaseService;
import com.company.project.dao.CourseRepository;
import com.company.project.domain.Course;

import com.company.project.dto.CourseCreateDTO;
import com.company.project.dto.CourseUpdateDTO;
import com.company.project.mapper.CourseMapper;
import com.company.project.vo.CourseVO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseService extends BaseService<Course, CourseVO, CourseCreateDTO, CourseUpdateDTO, Long> {
    private final CourseRepository repository;
    @Getter
    private final CourseMapper mapper;
    public CourseService(CourseRepository repository, CourseMapper mapper) {
        super(repository, Course.class, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected Long getIdFromUpdateDto(CourseUpdateDTO dto) {
        return dto.getId();
    }
}
