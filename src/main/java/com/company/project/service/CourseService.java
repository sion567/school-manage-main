package com.company.project.service;

import com.company.project.core.service.BaseService;
import com.company.project.dao.CourseRepository;
import com.company.project.domain.Course;

import com.company.project.domain.CourseSchedule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CourseService extends BaseService<Course, Long> {
    private final CourseRepository repository;
    public CourseService(CourseRepository repository, ModelMapper modelMapper) {
        super(repository, Course.class, modelMapper);
        this.repository = repository;
    }
}
