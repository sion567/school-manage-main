package com.conpany.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.company.project.dao.CourseRepository;
import com.company.project.dao.TeacherRepository;
import com.company.project.domain.Course;
import com.company.project.domain.Teacher;
import com.company.project.service.CourseService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCourses() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("数学");

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("语文");

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<Course> courses = courseService.findAll();

        assertThat(courses).hasSize(2);
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testGetCourseById_Success() {
        Course course = new Course();
        course.setId(1L);
        course.setName("英语");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("英语");
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCourseById_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Course> result = courseService.findById(1L);

        assertThat(result).isNotPresent();
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveCourse() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("张");
        teacher.setLastName("老师");

        Course course = new Course();
        course.setName("物理");
        course.setTeacher(teacher);

        Course savedCourse = new Course();
        savedCourse.setId(1L);
        savedCourse.setName("物理");
        savedCourse.setTeacher(teacher);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        Course result = courseService.create(course);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("物理");
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testDeleteCourse() {
        courseService.deleteById(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }
}