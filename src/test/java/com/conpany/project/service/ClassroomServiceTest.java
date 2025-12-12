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
import com.company.project.domain.Classroom;
import com.company.project.domain.Grade;
import com.company.project.service.ClassroomService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClassroomServiceTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private ClassroomService classroomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllClassrooms() {
        Classroom classroom1 = new Classroom();
        classroom1.setId(1L);
        classroom1.setName("一班");

        Classroom classroom2 = new Classroom();
        classroom2.setId(2L);
        classroom2.setName("二班");

        when(classroomRepository.findAll()).thenReturn(Arrays.asList(classroom1, classroom2));

        List<Classroom> classrooms = classroomService.findAll();

        assertThat(classrooms).hasSize(2);
        verify(classroomRepository, times(1)).findAll();
    }

    @Test
    public void testGetClassroomById_Success() {
        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("三班");

        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));

        Optional<Classroom> result = classroomService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("三班");
        verify(classroomRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetClassroomById_NotFound() {
        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Classroom> result = classroomService.findById(1L);

        assertThat(result).isNotPresent();
        verify(classroomRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveClassroom() {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setName("一年级");

        Classroom classroom = new Classroom();
        classroom.setName("四班");
        classroom.setGrade(grade);

        Classroom savedClassroom = new Classroom();
        savedClassroom.setId(1L);
        savedClassroom.setName("四班");
        savedClassroom.setGrade(grade);

        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(savedClassroom);

        Classroom result = classroomService.create(classroom);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("四班");
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    public void testDeleteClassroom() {
        classroomService.deleteById(1L);

        verify(classroomRepository, times(1)).deleteById(1L);
    }
}