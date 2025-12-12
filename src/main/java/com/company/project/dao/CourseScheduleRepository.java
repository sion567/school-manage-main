package com.company.project.dao;

import com.company.project.core.dao.BaseRepository;
import com.company.project.domain.CourseSchedule;

import org.springframework.stereotype.Repository;

@Repository
public interface CourseScheduleRepository extends BaseRepository<CourseSchedule, Long> {
}
