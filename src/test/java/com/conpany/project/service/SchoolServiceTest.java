package com.conpany.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.company.project.dao.SchoolRepository;
import com.company.project.domain.School;
import com.company.project.service.SchoolService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SchoolService schoolService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSchools() {
        School school1 = new School();
        school1.setId(1L);
        school1.setName("学校1");

        School school2 = new School();
        school2.setId(2L);
        school2.setName("学校2");

        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school1, school2));

        List<School> schools = schoolService.findAll();

        assertThat(schools).hasSize(2);
        verify(schoolRepository, times(1)).findAll();
    }

    @Test
    public void testGetSchoolById_Success() {
        School school = new School();
        school.setId(1L);
        school.setName("测试学校");

        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));

        Optional<School> result = schoolService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("测试学校");
        verify(schoolRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetSchoolById_NotFound() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<School> result = schoolService.findById(1L);

        assertThat(result).isNotPresent();
        verify(schoolRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveSchool() {
        School school = new School();
        school.setName("新学校");

        School savedSchool = new School();
        savedSchool.setId(1L);
        savedSchool.setName("新学校");

        when(schoolRepository.save(any(School.class))).thenReturn(savedSchool);

        School result = schoolService.create(school);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("新学校");
        verify(schoolRepository, times(1)).save(school);
    }

    @Test
    public void testDeleteSchool() {
        schoolService.deleteById(1L);

        verify(schoolRepository, times(1)).deleteById(1L);
    }
}
