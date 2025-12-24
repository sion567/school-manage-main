package com.company.project.web;

import com.company.project.core.web.CrudController;
import com.company.project.domain.School;
import com.company.project.dto.SchoolCreateDTO;
import com.company.project.dto.SchoolUpdateDTO;
import com.company.project.service.SchoolService;

import com.company.project.vo.SchoolVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(SchoolController.BASE_PATH)
public class SchoolController extends CrudController<School, SchoolVO, SchoolCreateDTO, SchoolUpdateDTO, Long> {

    static final String BASE_PATH = "/ui/schools";
    static final String LIST_VIEW = "schools/list";
    static final String INPUT_VIEW = "schools/input";
    static final String EDIT_VIEW = "schools/edit";

    private final SchoolService service;

    public SchoolController(SchoolService service) {
        super(service, BASE_PATH, LIST_VIEW, INPUT_VIEW, EDIT_VIEW);
        this.service = service;
    }

    @Override
    protected SchoolCreateDTO createNewDto() {
        return new SchoolCreateDTO();
    }

    @ModelAttribute("activePage")
    public String getActivePage() {
        return "schools";
    }
}