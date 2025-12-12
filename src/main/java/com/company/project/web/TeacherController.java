package com.company.project.web;


import com.company.project.domain.School;
import com.company.project.domain.Teacher;
import com.company.project.service.SchoolService;
import com.company.project.service.TeacherService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(TeacherController.BASE_PATH)
@RequiredArgsConstructor
public class TeacherController {
    static final String BASE_PATH = "/ui/teachers";

    public static String getRedirectPath() {
        return "redirect:" + BASE_PATH;
    }

    public static String getRedirectPathWithSlash() {
        return "redirect:" + BASE_PATH + "/";
    }


    private final TeacherService service;
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
        return "teachers";
    }

    @GetMapping
    public String listGrades(@RequestParam(value = "schoolId", required = false) Long schoolId, Model model) {
        if (schoolId != null) {
            model.addAttribute("teachers", service.findBySchoolIdWithSchool(schoolId));
        } else {
            model.addAttribute("teachers", service.findAllWithSchool());
        }
        model.addAttribute("selectedSchoolId", schoolId);

        return "teachers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teachers/form";
    }

    @PostMapping
    public String createTeacher(@ModelAttribute Teacher teacher) {
        service.create(teacher);
        return getRedirectPath();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("teacher", service.findById(id).orElseThrow());
        return "teachers/form";
    }

    @PostMapping("/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute Teacher teacher) {
        teacher.setId(id);
        service.update(teacher);
        return getRedirectPath();
    }

    @GetMapping("/{id}/delete")
    public String deleteTeacher(@PathVariable Long id) {
        service.deleteById(id);
        return getRedirectPath();
    }
}