package com.company.project.domain;

import java.util.List;

import com.company.project.core.model.DateAuditEntity;
import com.company.project.vo.ClassroomDetailRecord;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_classrooms")
@NamedQueries({
        @NamedQuery(
                name = "Classroom.findWithGradeAndTeacher",
                query = "SELECT c FROM Classroom c " +
                        "LEFT JOIN FETCH c.grade g " +
                        "LEFT JOIN FETCH c.teacher t " +
                        "WHERE c.id = :id"
        ),
        @NamedQuery(
                name = "Classroom.findAllWithDetails",
                query = "SELECT c FROM Classroom c " +
                        "LEFT JOIN FETCH c.grade g " +
                        "LEFT JOIN FETCH c.teacher t"
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Classroom.findAllWithNames",
                query = "SELECT c.id, c.name, g.name as grade_name,t.first_name as teacher_first_name, t.last_name as teacher_last_name " +
                        "FROM tbl_classrooms c " +
                        "LEFT JOIN tbl_grades g ON c.grade_id = g.id " +
                        "LEFT JOIN tbl_teachers t ON c.teacher_id = t.id",
                resultSetMapping = "ClassroomWithNames"
        ),
        @NamedNativeQuery(
                name = "Classroom.findByGradeIdWithNames",
                query = "SELECT c.id, c.name, g.name as grade_name,t.first_name as teacher_first_name, t.last_name as teacher_last_name " +
                        "FROM tbl_classrooms c " +
                        "LEFT JOIN tbl_grades g ON c.grade_id = g.id " +
                        "LEFT JOIN tbl_teachers t ON c.teacher_id = t.id " +
                        "WHERE c.grade_id = :gradeId",
                resultSetMapping = "ClassroomWithNames"
        )


})
@SqlResultSetMapping(
        name = "ClassroomWithNames",
        classes = {
                @ConstructorResult(
                        targetClass = ClassroomDetailRecord.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "grade_name", type = String.class),
                                @ColumnResult(name = "teacher_first_name", type = String.class),
                                @ColumnResult(name = "teacher_last_name", type = String.class)
                        }
                )
        }
)
@Getter
@Setter
public class Classroom extends DateAuditEntity {
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gradeId")
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId")
    private Teacher teacher;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseSchedule> schedules;
}
