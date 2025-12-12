package com.conpany.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.company.project.dao.ClassroomRepository;
import com.company.project.dao.GradeRepository;
import com.company.project.dao.SchoolRepository;
import com.company.project.dao.StudentRepository;
import com.company.project.domain.Classroom;
import com.company.project.domain.Grade;
import com.company.project.domain.Student;
import com.company.project.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStudents() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("小");
        student1.setLastName("明");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("小");
        student2.setLastName("红");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<Student> students = studentService.findAll();

        assertThat(students).hasSize(2);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetStudentById_Success() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("小");
        student.setLastName("刚");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getFullName()).isEqualTo("小 刚");
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.findById(1L);

        assertThat(result).isNotPresent();
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveStudent() {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setName("一年级");

        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("一班");

        Student student = new Student();
        student.setFirstName("小");
        student.setLastName("李");
        student.setGrade(grade);
        student.setClassroom(classroom);

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setFirstName("小");
        savedStudent.setLastName("李");
        savedStudent.setGrade(grade);
        savedStudent.setClassroom(classroom);

        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        Student result = studentService.create(student);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFullName()).isEqualTo("小 李");
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testDeleteStudent() {
        studentService.deleteById(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
