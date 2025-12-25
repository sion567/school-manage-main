package com.company.project.web;


import com.company.project.core.util.RequestUtils;
import com.company.project.core.web.CrudController;
import com.company.project.domain.School;
import com.company.project.domain.Teacher;
import com.company.project.dto.SchoolCreateDTO;
import com.company.project.dto.SchoolUpdateDTO;
import com.company.project.dto.TeacherCreateDTO;
import com.company.project.dto.TeacherUpdateDTO;
import com.company.project.service.SchoolService;
import com.company.project.service.TeacherService;

import com.company.project.vo.SchoolVO;
import com.company.project.vo.TeacherVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(TeacherController.BASE_PATH)
public class TeacherController extends CrudController<Teacher, TeacherVO, TeacherCreateDTO, TeacherUpdateDTO, Long> {
    static final String BASE_PATH = "/ui/teachers";
    static final String LIST_VIEW = "teachers/list";
    static final String INPUT_VIEW = "teachers/input";
    static final String EDIT_VIEW = "teachers/edit";

    private final TeacherService service;
    private final SchoolService schoolService;

    public TeacherController(TeacherService service, SchoolService schoolService) {
        super(service, BASE_PATH, LIST_VIEW, INPUT_VIEW, EDIT_VIEW);
        this.service = service;
        this.schoolService = schoolService;
    }

    @ModelAttribute("schools")
    public Iterable<SchoolVO> schools() {
        return schoolService.findAll();
    }

    public String listWithSpecialLogic(HttpServletRequest req, Model model) {
        Long schoolId = RequestUtils.getParameterAsLong(req, "schoolId");
        if (schoolId != null) {
            model.addAttribute("teachers", service.findBySchoolIdWithSchool(schoolId));
        } else {
            model.addAttribute("teachers", service.findAllWithSchool());
        }
        model.addAttribute("selectedSchoolId", schoolId);
        return "classrooms/list";
    }

    @Override
    protected TeacherCreateDTO createNewDto() {
        return new TeacherCreateDTO();
    }

    @ModelAttribute("activePage")
    public String getActivePage() {
        return "teachers";
    }
}