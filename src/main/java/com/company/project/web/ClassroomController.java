package com.company.project.web;

import java.util.List;

import com.company.project.domain.Classroom;
import com.company.project.domain.Grade;
import com.company.project.domain.School;
import com.company.project.domain.Teacher;
import com.company.project.service.ClassroomService;
import com.company.project.service.GradeService;
import com.company.project.service.SchoolService;
import com.company.project.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(ClassroomController.BASE_PATH)
@RequiredArgsConstructor
public class ClassroomController {
    public static final String BASE_PATH = "/ui/classrooms";

    public static String getRedirectPath() {
        return "redirect:" + BASE_PATH;
    }

    public static String getRedirectPathWithSlash() {
        return "redirect:" + BASE_PATH + "/";
    }

    private final ClassroomService service;
    private final GradeService gradeService;
    private final TeacherService teacherService;
    private final SchoolService schoolService;

    @ModelAttribute("grades")
    public Iterable<Grade> grades() {
        return gradeService.findAll();
    }
    @ModelAttribute("teachers")
    public Iterable<Teacher> teachers() {
        return teacherService.findAll();
    }
    @ModelAttribute("basePath")
    public String getBasePath() {
        return BASE_PATH;
    }
    @ModelAttribute("activePage")
    public String getActivePage() {
        return "classrooms";
    }

    @GetMapping
    public String listClassrooms(
            @RequestParam(value = "gradeId", required = false) Long gradeId,
            Model model) {
        if (gradeId != null) {
            model.addAttribute("classrooms", service.findByGradeIdWithNames(gradeId));
        } else {
            model.addAttribute("classrooms", service.findAllWithNames());
        }
        model.addAttribute("selectedGradeId", gradeId);
        return "classrooms/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("classroom", new Classroom());
        return "classrooms/form";
    }

    @GetMapping("/school-select")
    public String schoolSelectWithParams(
            @RequestParam(value = "schoolId", required = false) Long schoolId,
            Model model) {
        model.addAttribute("classroom", new Classroom());
        List<School> schools = schoolService.findAll();
        model.addAttribute("schools", schools);

        if (schoolId != null) {
            List<Grade> grades = gradeService.findBySchoolId(schoolId);
            List<Teacher> teachers = teacherService.findBySchoolId(schoolId);

            model.addAttribute("selectedSchoolId", schoolId);
            model.addAttribute("grades", grades);
            model.addAttribute("teachers", teachers);
        }
        return "classrooms/form";
    }

    @GetMapping("/teacher-select")
    @ResponseBody
    public List<Teacher> searchTeachers(@RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return teacherService.findAll();
        }
        return teacherService.searchTeachers(keyword.trim());
    }

    @PostMapping
    public String createClassroom(@Valid @ModelAttribute Classroom classroom) {
        service.save(classroom);
        return getRedirectPath();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("classroom", service.findById(id).orElseThrow());
        return "classrooms/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteClassroom(@PathVariable Long id) {
        service.deleteById(id);
        return getRedirectPath();
    }

    private String redirectToCurrent() {
        return getRedirectPath(); // 最简洁的重定向
    }

    private String redirectToParent() {
        return "redirect:../";
    }
}