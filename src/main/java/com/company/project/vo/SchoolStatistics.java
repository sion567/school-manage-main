package com.company.project.vo;

import com.company.project.domain.School;

public record SchoolStatistics(School school, long totalTeachers, long totalStudents, long totalCourses) {
}
