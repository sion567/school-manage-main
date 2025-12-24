package com.company.project.web;

import java.util.List;

import com.company.project.core.web.CrudController;
import com.company.project.domain.Course;
import com.company.project.domain.Student;
import com.company.project.domain.Teacher;
import com.company.project.dto.CourseCreateDTO;
import com.company.project.dto.CourseUpdateDTO;
import com.company.project.dto.StudentCreateDTO;
import com.company.project.dto.StudentUpdateDTO;
import com.company.project.service.CourseService;
import com.company.project.service.GradeService;
import com.company.project.service.SchoolService;
import com.company.project.service.TeacherService;

import com.company.project.vo.CourseVO;
import com.company.project.vo.StudentVO;
import com.company.project.vo.TeacherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(CourseController.BASE_PATH)
public class CourseController extends CrudController<Course, CourseVO, CourseCreateDTO, CourseUpdateDTO, Long> {
    static final String BASE_PATH = "/ui/courses";
    static final String LIST_VIEW = "courses/list";
    static final String INPUT_VIEW = "courses/input";
    static final String EDIT_VIEW = "courses/edit";

    private final CourseService service;
    private final TeacherService teacherService;

    public CourseController(CourseService service, TeacherService teacherService) {
        super(service, BASE_PATH, LIST_VIEW, INPUT_VIEW, EDIT_VIEW);
        this.service = service;
        this.teacherService = teacherService;
    }

    @ModelAttribute("teachers")
    public Iterable<TeacherVO> teachers() {
        return teacherService.findAll();
    }

    @ModelAttribute("activePage")
    public String getActivePage() {
        return "courses";
    }

    @Override
    protected CourseCreateDTO createNewDto() {
        return new CourseCreateDTO();
    }

}
