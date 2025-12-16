package com.company.project.web;

import java.util.List;

import com.company.project.domain.Course;
import com.company.project.domain.Teacher;
import com.company.project.service.CourseService;
import com.company.project.service.SchoolService;
import com.company.project.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(CourseController.BASE_PATH)
@RequiredArgsConstructor
public class CourseController {
    static final String BASE_PATH = "/ui/courses";

    public static String getRedirectPath() {
        return "redirect:" + BASE_PATH;
    }

    public static String getRedirectPathWithSlash() {
        return "redirect:" + BASE_PATH + "/";
    }

    private final CourseService service;
    private final TeacherService teacherService;
    private final SchoolService schoolService;

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
        return "courses";
    }

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", service.findAll());
        return "courses/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "courses/form";
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
    public String createCourse(@Valid @ModelAttribute Course course) {
        service.save(course);
        return getRedirectPath();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", service.findById(id).orElseThrow());
        return "courses/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        service.deleteById(id);
        return getRedirectPath();
    }
}
