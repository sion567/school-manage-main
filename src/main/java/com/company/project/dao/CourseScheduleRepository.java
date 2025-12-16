package com.company.project.dao;

import com.company.project.domain.CourseSchedule;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseScheduleRepository extends JpaRepositoryImplementation<CourseSchedule, Long> {
}
