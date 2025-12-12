package com.company.project.domain;

import java.util.List;

import com.company.project.core.model.DateAuditEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_schools")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Schema(description = "学校实体")
public class School extends DateAuditEntity {
    @Column
    @NotBlank(message = "用户名不能为空")
    @Size(max = 100, message = "学校名称不能超过100个字符")
    private String name;

    @Valid
    @Embedded
    private ContactInfo contactInfo = new ContactInfo();

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Grade> grades;

    @OneToMany(mappedBy = "school")
    private List<Student> students;

    @OneToMany(mappedBy = "school")
    private List<Teacher> teachers;

    public void addGrade(Grade grade) {
        this.grades.add(grade);
        grade.setSchool(this);
    }

    public void removeGrade(Grade grade) {
        this.grades.remove(grade);
        grade.setSchool(null);
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.setSchool(this);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.setSchool(null);
    }
}