package com.company.project.service;

import com.company.project.core.service.BaseService;
import com.company.project.dao.CourseScheduleRepository;
import com.company.project.domain.CourseSchedule;

import com.company.project.dto.CourseScheduleCreateDTO;
import com.company.project.dto.CourseScheduleUpdateDTO;
import com.company.project.mapper.CourseScheduleMapper;
import com.company.project.vo.CourseScheduleVO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CourseScheduleService extends BaseService<CourseSchedule, CourseScheduleVO, CourseScheduleCreateDTO, CourseScheduleUpdateDTO, Long> {
    private final CourseScheduleRepository repository;
    @Getter
    private final CourseScheduleMapper mapper;
    public CourseScheduleService(CourseScheduleRepository repository, CourseScheduleMapper mapper) {
        super(repository, CourseSchedule.class, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected Long getIdFromUpdateDto(CourseScheduleUpdateDTO dto) {
        return dto.getId();
    }
}

