package com.conpany.project.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import com.company.project.domain.School;
import com.company.project.service.SchoolService;
import com.company.project.web.SchoolController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SchoolControllerTest {

    @Mock
    private SchoolService schoolService;

    @InjectMocks
    private SchoolController schoolController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(schoolController).build();
    }

    @Test
    public void testListSchools() throws Exception {
        School school1 = new School();
        school1.setId(1L);
        school1.setName("学校1");

        School school2 = new School();
        school2.setId(2L);
        school2.setName("学校2");

        when(schoolService.findAll()).thenReturn(Arrays.asList(school1, school2));

        mockMvc.perform(get("/schools"))
                .andExpect(status().isOk())
                .andExpect(view().name("schools/list"))
                .andExpect(model().attributeExists("schools"));

        verify(schoolService, times(1)).findAll();
    }

    @Test
    public void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/schools/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("schools/form"))
                .andExpect(model().attributeExists("school"));
    }

    @Test
    public void testCreateSchool() throws Exception {
        School school = new School();
        school.setName("新学校");

        School savedSchool = new School();
        savedSchool.setId(1L);
        savedSchool.setName("新学校");

        when(schoolService.create(any(School.class))).thenReturn(savedSchool);

        mockMvc.perform(post("/schools")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "新学校"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/schools"));

        verify(schoolService, times(1)).create(any(School.class));
    }

    @Test
    public void testShowEditForm() throws Exception {
        School school = new School();
        school.setId(1L);
        school.setName("测试学校");

        when(schoolService.findById(1L)).thenReturn(Optional.of(school));

        mockMvc.perform(get("/schools/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("schools/form"))
                .andExpect(model().attributeExists("school"));

        verify(schoolService, times(1)).findById(1L);
    }

    @Test
    public void testUpdateSchool() throws Exception {
        School school = new School();
        school.setId(1L);
        school.setName("更新后的学校");

        when(schoolService.update(any(School.class))).thenReturn(school);

        mockMvc.perform(post("/schools/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("name", "更新后的学校"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/schools"));

        verify(schoolService, times(1)).update(any(School.class));
    }

    @Test
    public void testDeleteSchool() throws Exception {
        mockMvc.perform(get("/schools/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/schools"));

        verify(schoolService, times(1)).deleteById(1L);
    }
}