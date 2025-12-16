package com.company.project.web;

import java.util.List;

import com.company.project.core.exception.ResourceNotFoundException;
import com.company.project.domain.Grade;
import com.company.project.domain.School;
import com.company.project.domain.Teacher;
import com.company.project.service.GradeService;
import com.company.project.service.SchoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(GradeController.BASE_PATH)
@RequiredArgsConstructor
public class GradeController {
    static final String BASE_PATH = "/ui/grades";

    public static String getRedirectPath() {
        return "redirect:" + BASE_PATH;
    }

    public static String getRedirectPathWithSlash() {
        return "redirect:" + BASE_PATH + "/";
    }
    private final GradeService service;
    private final SchoolService schoolService;

    @ModelAttribute("schools")
    public Iterable<School> schools() {
        return schoolService.findAll();
    }
    @ModelAttribute("basePath")
    public String getBasePath() {
        return BASE_PATH;
    }
    @ModelAttribute("activePage")
    public String getActivePage() {
        return "grades";
    }

    @GetMapping
    public String listGrades(@RequestParam(value = "schoolId", required = false) Long schoolId, Model model) {
        if (schoolId != null) {
            model.addAttribute("grades", service.findBySchoolIdWithSchool(schoolId));
        } else {
            model.addAttribute("grades", service.findAllWithSchool());
        }
        model.addAttribute("selectedSchoolId", schoolId);

        return "grades/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("grade", new Grade());
        return "grades/form";
    }

    @PostMapping
    public String createGrade(@Valid @ModelAttribute Grade grade) {
        service.save(grade);
        return getRedirectPath();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("grade", service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Grade not found", "Grade Id:" + id)));
        return "grades/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteGrade(@PathVariable Long id) {
        service.deleteById(id);
        return getRedirectPath();
    }
}