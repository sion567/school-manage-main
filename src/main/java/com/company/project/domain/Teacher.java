package com.company.project.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_teachers")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
    @Embedded
    private PersonalInfo personalInfo = new PersonalInfo();;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address.province", column = @Column(name = "work_province")),
            @AttributeOverride(name = "address.city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "address.district", column = @Column(name = "work_district")),
            @AttributeOverride(name = "address.street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "address.detailAddress", column = @Column(name = "work_detailAddress")),
            @AttributeOverride(name = "address.postalCode", column = @Column(name = "work_postalCode")),
            @AttributeOverride(name = "email", column = @Column(name = "work_email")),
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "work_phone_number"))
    })
    private ContactInfo work = new ContactInfo();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Classroom> classrooms;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseSchedule> schedules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schoolId")
    private School school;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column
    private String department;

    /**
     * 专业
     */
    @Column(name = "specialization")
    private String specialization;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

}
