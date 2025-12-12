package com.conpany.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.company.project.dao.GradeRepository;
import com.company.project.dao.SchoolRepository;
import com.company.project.domain.Grade;
import com.company.project.domain.School;
import com.company.project.service.GradeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private GradeService gradeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllGrades() {
        Grade grade1 = new Grade();
        grade1.setId(1L);
        grade1.setName("一年级");

        Grade grade2 = new Grade();
        grade2.setId(2L);
        grade2.setName("二年级");

        when(gradeRepository.findAll()).thenReturn(Arrays.asList(grade1, grade2));

        List<Grade> grades = gradeService.findAll();

        assertThat(grades).hasSize(2);
        verify(gradeRepository, times(1)).findAll();
    }

    @Test
    public void testGetGradeById_Success() {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setName("三年级");

        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        Optional<Grade> result = gradeService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("三年级");
        verify(gradeRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetGradeById_NotFound() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Grade> result = gradeService.findById(1L);

        assertThat(result).isNotPresent();
        verify(gradeRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveGrade() {
        School school = new School();
        school.setId(1L);
        school.setName("测试学校");

        Grade grade = new Grade();
        grade.setName("四年级");
        grade.setSchool(school);

        Grade savedGrade = new Grade();
        savedGrade.setId(1L);
        savedGrade.setName("四年级");
        savedGrade.setSchool(school);

        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(gradeRepository.save(any(Grade.class))).thenReturn(savedGrade);

        Grade result = gradeService.create(grade);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("四年级");
        verify(gradeRepository, times(1)).save(grade);
    }

    @Test
    public void testDeleteGrade() {
        gradeService.deleteById(1L);

        verify(gradeRepository, times(1)).deleteById(1L);
    }
}
