package com.conpany.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import com.company.project.Application;
import com.company.project.dao.ClassroomRepository;
import com.company.project.dao.GradeRepository;
import com.company.project.dao.SchoolRepository;
import com.company.project.domain.Classroom;
import com.company.project.domain.Grade;
import com.company.project.domain.School;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@Import({ClassroomRepository.class, GradeRepository.class, SchoolRepository.class})
public class ClassroomRepositoryTest {

    private final Pageable pageable = Pageable.unpaged();

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void testSaveClassroom() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Grade grade = new Grade();
        grade.setName("一年级");
        grade.setSchool(savedSchool);
        Grade savedGrade = gradeRepository.save(grade);

        Classroom classroom = new Classroom();
        classroom.setName("一班");
        classroom.setGrade(savedGrade);

        Classroom savedClassroom = classroomRepository.save(classroom);

        assertThat(savedClassroom.getId()).isNotNull();
        assertThat(savedClassroom.getName()).isEqualTo("一班");
        assertThat(savedClassroom.getGrade().getId()).isEqualTo(savedGrade.getId());
    }

    @Test
    public void testFindAllClassrooms() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Grade grade = new Grade();
        grade.setName("一年级");
        grade.setSchool(savedSchool);
        Grade savedGrade = gradeRepository.save(grade);

        Classroom classroom1 = new Classroom();
        classroom1.setName("一班");
        classroom1.setGrade(savedGrade);

        Classroom classroom2 = new Classroom();
        classroom2.setName("二班");
        classroom2.setGrade(savedGrade);

        classroomRepository.save(classroom1);
        classroomRepository.save(classroom2);

        List<Classroom> classrooms = classroomRepository.findAll();

        assertThat(classrooms).hasSize(2);
        assertThat(classrooms).extracting(Classroom::getName).containsExactlyInAnyOrder("一班", "二班");
    }

    @Test
    public void testFindClassroomById() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Grade grade = new Grade();
        grade.setName("一年级");
        grade.setSchool(savedSchool);
        Grade savedGrade = gradeRepository.save(grade);

        Classroom classroom = new Classroom();
        classroom.setName("三班");
        classroom.setGrade(savedGrade);
        Classroom savedClassroom = classroomRepository.save(classroom);

        Optional<Classroom> foundClassroom = classroomRepository.findById(savedClassroom.getId());

        assertThat(foundClassroom).isPresent();
        assertThat(foundClassroom.get().getId()).isEqualTo(savedClassroom.getId());
        assertThat(foundClassroom.get().getName()).isEqualTo("三班");
    }

    @Test
    public void testDeleteClassroom() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Grade grade = new Grade();
        grade.setName("一年级");
        grade.setSchool(savedSchool);
        Grade savedGrade = gradeRepository.save(grade);

        Classroom classroom = new Classroom();
        classroom.setName("四班");
        classroom.setGrade(savedGrade);
        Classroom savedClassroom = classroomRepository.save(classroom);

        classroomRepository.deleteById(savedClassroom.getId());

        Optional<Classroom> deletedClassroom = classroomRepository.findById(savedClassroom.getId());
        assertThat(deletedClassroom).isNotPresent();
    }
}