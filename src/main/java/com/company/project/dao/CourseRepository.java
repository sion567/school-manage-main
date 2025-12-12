package com.company.project.dao;

import com.company.project.core.dao.BaseRepository;
import com.company.project.domain.Course;

import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends BaseRepository<Course, Long> {
}
