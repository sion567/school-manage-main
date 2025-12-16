package com.company.project.dao;

import com.company.project.domain.Course;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepositoryImplementation<Course, Long> {
}
