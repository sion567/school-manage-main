package com.conpany.project.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import com.company.project.dao.ClassroomRepository;
import com.company.project.dao.GradeRepository;
import com.company.project.dao.SchoolRepository;
import com.company.project.dao.StudentRepository;
import com.company.project.domain.Classroom;
import com.company.project.domain.Grade;
import com.company.project.domain.School;
import com.company.project.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void testSaveStudent() {
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

        Student student = new Student();
        student.setFirstName("小");
        student.setLastName("明");
        student.setGrade(savedGrade);
        student.setClassroom(savedClassroom);

        Student savedStudent = studentRepository.save(student);

        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getFullName()).isEqualTo("小 明");
        assertThat(savedStudent.getGrade().getId()).isEqualTo(savedGrade.getId());
        assertThat(savedStudent.getClassroom().getId()).isEqualTo(savedClassroom.getId());
    }

    @Test
    public void testFindAllStudents() {
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

        Student student1 = new Student();
        student1.setFirstName("小");
        student1.setLastName("红");
        student1.setGrade(savedGrade);
        student1.setClassroom(savedClassroom);

        Student student2 = new Student();
        student2.setFirstName("小");
        student2.setLastName("刚");
        student2.setGrade(savedGrade);
        student2.setClassroom(savedClassroom);

        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Student> students = studentRepository.findAll();

        assertThat(students).hasSize(2);
        assertThat(students).extracting(s -> s.getFullName())
                .containsExactlyInAnyOrder("小 红", "小 刚");
    }

    @Test
    public void testFindStudentById() {
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

        Student student = new Student();
        student.setFirstName("小");
        student.setLastName("李");
        student.setGrade(savedGrade);
        student.setClassroom(savedClassroom);
        Student savedStudent = studentRepository.save(student);

        Optional<Student> foundStudent = studentRepository.findById(savedStudent.getId());

        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getId()).isEqualTo(savedStudent.getId());
        assertThat(foundStudent.get().getFullName()).isEqualTo("小 李");
    }

    @Test
    public void testDeleteStudent() {
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

        Student student = new Student();
        student.setFirstName("小");
        student.setLastName("王");
        student.setGrade(savedGrade);
        student.setClassroom(savedClassroom);
        Student savedStudent = studentRepository.save(student);

        studentRepository.deleteById(savedStudent.getId());

        Optional<Student> deletedStudent = studentRepository.findById(savedStudent.getId());
        assertThat(deletedStudent).isNotPresent();
    }
}
