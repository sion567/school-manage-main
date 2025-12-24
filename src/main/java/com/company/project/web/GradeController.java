package com.company.project.web;

import com.company.project.core.exception.ResourceNotFoundException;
import com.company.project.core.web.CrudController;
import com.company.project.domain.Grade;
import com.company.project.domain.School;
import com.company.project.dto.GradeCreateDTO;
import com.company.project.dto.GradeUpdateDTO;
import com.company.project.service.GradeService;
import com.company.project.service.SchoolService;

import com.company.project.vo.GradeVO;
import com.company.project.vo.SchoolSimpleVO;
import com.company.project.vo.SchoolVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping(GradeController.BASE_PATH)
public class GradeController extends CrudController<Grade, GradeVO, GradeCreateDTO, GradeUpdateDTO, Long> {
    static final String BASE_PATH = "/ui/grades";
    static final String LIST_VIEW = "grades/list";
    static final String INPUT_VIEW = "grades/input";
    static final String EDIT_VIEW = "grades/edit";

    private final GradeService service;
    private final SchoolService schoolService;

    public GradeController(GradeService service, SchoolService schoolService) {
        super(service, BASE_PATH, LIST_VIEW, INPUT_VIEW, EDIT_VIEW);
        this.service = service;
        this.schoolService = schoolService;
    }

    @ModelAttribute("schools")
    public Iterable<SchoolSimpleVO> schools() {
        return schoolService.findAllSimple();
    }

    @ModelAttribute("activePage")
    public String getActivePage() {
        return "grades";
    }

    @Override
    protected GradeCreateDTO createNewDto() {
        return new GradeCreateDTO();
    }

    @GetMapping
    public String list(@RequestParam(value = "schoolId", required = false) Long schoolId, Model model) {
        if (schoolId != null) {
            model.addAttribute("grades", service.findBySchoolIdWithSchool(schoolId));
        } else {
            model.addAttribute("grades", service.findAllWithSchool());
        }
        model.addAttribute("selectedSchoolId", schoolId);

        return LIST_VIEW;
    }
}