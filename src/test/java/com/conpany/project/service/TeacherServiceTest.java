package com.conpany.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.company.project.dao.TeacherRepository;
import com.company.project.domain.Teacher;
import com.company.project.service.TeacherService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTeachers() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("张");
        teacher1.setLastName("老师");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("李");
        teacher2.setLastName("老师");

        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        List<Teacher> teachers = teacherService.findAll();

        assertThat(teachers).hasSize(2);
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testGetTeacherById_Success() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("王");
        teacher.setLastName("五");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Optional<Teacher> result = teacherService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getFullName()).isEqualTo("王 五");
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTeacherById_NotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Teacher> result = teacherService.findById(1L);

        assertThat(result).isNotPresent();
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("赵");
        teacher.setLastName("六");

        Teacher savedTeacher = new Teacher();
        savedTeacher.setId(1L);
        savedTeacher.setFirstName("赵");
        savedTeacher.setLastName("六");

        when(teacherRepository.save(any(Teacher.class))).thenReturn(savedTeacher);

        Teacher result = teacherService.create(teacher);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFullName()).isEqualTo("赵 六");
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    public void testDeleteTeacher() {
        teacherService.deleteById(1L);

        verify(teacherRepository, times(1)).deleteById(1L);
    }
}