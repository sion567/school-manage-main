package com.conpany.project.web.api;

import com.company.project.domain.School;
import com.company.project.service.SchoolService;
import com.company.project.web.api.ApiSchoolController;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ApiSchoolControllerTest {

    @Mock
    private SchoolService schoolService;

    @InjectMocks
    private ApiSchoolController apiSchoolController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiSchoolController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllSchools() throws Exception {
        School school1 = new School();
        school1.setId(1L);
        school1.setName("学校1");

        School school2 = new School();
        school2.setId(2L);
        school2.setName("学校2");

        when(schoolService.findAll()).thenReturn(Arrays.asList(school1, school2));

        mockMvc.perform(get("/api/v1/schools")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("学校1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("学校2"));

        verify(schoolService, times(1)).findAll();
    }

    @Test
    public void testGetSchoolById_Success() throws Exception {
        School school = new School();
        school.setId(1L);
        school.setName("测试学校");

        when(schoolService.findById(1L)).thenReturn(Optional.of(school));

        mockMvc.perform(get("/api/v1/schools/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("测试学校"))
                .andExpect(jsonPath("$.success").value(true));

        verify(schoolService, times(1)).findById(1L);
    }

    @Test
    public void testGetSchoolById_NotFound() throws Exception {
        when(schoolService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/schools/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("学校不存在"));

        verify(schoolService, times(1)).findById(1L);
    }

    @Test
    public void testCreateSchool() throws Exception {
        School school = new School();
        school.setName("新学校");

        School savedSchool = new School();
        savedSchool.setId(1L);
        savedSchool.setName("新学校");

        when(schoolService.create(any(School.class))).thenReturn(savedSchool);

        mockMvc.perform(post("/api/v1/schools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(school)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("新学校"))
                .andExpect(jsonPath("$.success").value(true));

        verify(schoolService, times(1)).create(any(School.class));
    }

    @Test
    public void testUpdateSchool() throws Exception {
        School school = new School();
        school.setId(1L);
        school.setName("更新后的学校");

        when(schoolService.save(any(School.class))).thenReturn(school);

        mockMvc.perform(put("/api/v1/schools/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(school)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("更新后的学校"))
                .andExpect(jsonPath("$.success").value(true));

        verify(schoolService, times(1)).save(any(School.class));
    }

    @Test
    public void testDeleteSchool() throws Exception {
        mockMvc.perform(delete("/api/v1/schools/1"))
                .andExpect(status().isNoContent());

        verify(schoolService, times(1)).deleteById(1L);
    }
}