package com.company.project.web;

import com.company.project.core.web.CrudController;
import com.company.project.domain.Student;
import com.company.project.domain.Teacher;
import com.company.project.dto.StudentCreateDTO;
import com.company.project.dto.StudentUpdateDTO;
import com.company.project.dto.TeacherCreateDTO;
import com.company.project.dto.TeacherUpdateDTO;
import com.company.project.service.SchoolService;
import com.company.project.service.StudentService;

import com.company.project.service.TeacherService;
import com.company.project.vo.StudentVO;
import com.company.project.vo.TeacherVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(StudentController.BASE_PATH)
public class StudentController extends CrudController<Student, StudentVO, StudentCreateDTO, StudentUpdateDTO, Long> {
    static final String BASE_PATH = "/ui/students";
    static final String LIST_VIEW = "students/list";
    static final String INPUT_VIEW = "students/input";
    static final String EDIT_VIEW = "students/edit";

    private final StudentService service;

    public StudentController(StudentService service) {
        super(service, BASE_PATH, LIST_VIEW, INPUT_VIEW, EDIT_VIEW);
        this.service = service;
    }

    @ModelAttribute("basePath")
    public String getBasePath() {
        return BASE_PATH;
    }
    @ModelAttribute("activePage")
    public String getActivePage() {
        return "students";
    }

    @Override
    protected StudentCreateDTO createNewDto() {
        return new StudentCreateDTO();
    }

}