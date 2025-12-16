package com.conpany.project.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import com.company.project.dao.CourseRepository;
import com.company.project.dao.TeacherRepository;
import com.company.project.domain.Course;
import com.company.project.domain.Teacher;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void testSaveCourse() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("张");
        teacher.setLastName("老师");
        Teacher savedTeacher = teacherRepository.save(teacher);

        Course course = new Course();
        course.setName("数学");
        course.setDescription("数学课程");
        course.setCredits(3);
        course.setTeacher(savedTeacher);

        Course savedCourse = courseRepository.save(course);

        assertThat(savedCourse.getId()).isNotNull();
        assertThat(savedCourse.getName()).isEqualTo("数学");
        assertThat(savedCourse.getDescription()).isEqualTo("数学课程");
        assertThat(savedCourse.getCredits()).isEqualTo(3);
        assertThat(savedCourse.getTeacher().getId()).isEqualTo(savedTeacher.getId());
    }

    @Test
    public void testFindAllCourses() {
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("张");
        teacher1.setLastName("老师");
        Teacher savedTeacher1 = teacherRepository.save(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("李");
        teacher2.setLastName("老师");
        Teacher savedTeacher2 = teacherRepository.save(teacher2);

        Course course1 = new Course();
        course1.setName("数学");
        course1.setDescription("数学课程");
        course1.setCredits(3);
        course1.setTeacher(savedTeacher1);

        Course course2 = new Course();
        course2.setName("语文");
        course2.setDescription("语文课程");
        course2.setCredits(2);
        course2.setTeacher(savedTeacher2);

        courseRepository.save(course1);
        courseRepository.save(course2);

        List<Course> courses = courseRepository.findAll();

        assertThat(courses).hasSize(2);
        assertThat(courses).extracting(Course::getName).containsExactlyInAnyOrder("数学", "语文");
    }

    @Test
    public void testFindCourseById() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("王");
        teacher.setLastName("老师");
        Teacher savedTeacher = teacherRepository.save(teacher);

        Course course = new Course();
        course.setName("英语");
        course.setDescription("英语课程");
        course.setCredits(4);
        course.setTeacher(savedTeacher);
        Course savedCourse = courseRepository.save(course);

        Optional<Course> foundCourse = courseRepository.findById(savedCourse.getId());

        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getId()).isEqualTo(savedCourse.getId());
        assertThat(foundCourse.get().getName()).isEqualTo("英语");
        assertThat(foundCourse.get().getDescription()).isEqualTo("英语课程");
    }

    @Test
    public void testDeleteCourse() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("赵");
        teacher.setLastName("老师");
        Teacher savedTeacher = teacherRepository.save(teacher);

        Course course = new Course();
        course.setName("物理");
        course.setDescription("物理课程");
        course.setCredits(3);
        course.setTeacher(savedTeacher);
        Course savedCourse = courseRepository.save(course);

        courseRepository.deleteById(savedCourse.getId());

        Optional<Course> deletedCourse = courseRepository.findById(savedCourse.getId());
        assertThat(deletedCourse).isNotPresent();
    }
}