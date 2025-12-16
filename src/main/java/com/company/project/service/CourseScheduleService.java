package com.company.project.service;

import com.company.project.core.service.BaseService;
import com.company.project.dao.CourseScheduleRepository;
import com.company.project.domain.Classroom;
import com.company.project.domain.CourseSchedule;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CourseScheduleService extends BaseService<CourseSchedule, Long> {
    private final CourseScheduleRepository repository;
    public CourseScheduleService(CourseScheduleRepository repository, ModelMapper modelMapper) {
        super(repository, CourseSchedule.class, modelMapper);
        this.repository = repository;
    }

}