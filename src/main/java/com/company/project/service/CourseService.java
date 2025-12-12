package com.company.project.service;

import com.company.project.core.service.BaseService;
import com.company.project.dao.CourseRepository;
import com.company.project.domain.Course;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CourseService extends BaseService<Course, Long> {
    private final CourseRepository repository;
    public CourseService(CourseRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
