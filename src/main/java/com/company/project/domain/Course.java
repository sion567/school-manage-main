package com.company.project.domain;

import java.util.List;

import com.company.project.core.model.DateAuditEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_courses")
@Getter
@Setter
public class Course extends DateAuditEntity {
    private String name;
    @Column
    private String description;
    /**
     * 学分
     */
    @Column
    private Integer credits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId")
    private Teacher teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseSchedule> schedules;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
