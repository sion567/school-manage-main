package com.company.project.web;

import com.company.project.domain.Student;
import com.company.project.service.StudentService;

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
@RequiredArgsConstructor
public class StudentController {
    static final String BASE_PATH = "/ui/students";

    public static String getRedirectPath() {
        return "redirect:" + BASE_PATH;
    }

    public static String getRedirectPathWithSlash() {
        return "redirect:" + BASE_PATH + "/";
    }

    private final StudentService service;

    @ModelAttribute("basePath")
    public String getBasePath() {
        return BASE_PATH;
    }
    @ModelAttribute("activePage")
    public String getActivePage() {
        return "students";
    }


    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", service.findAll());
        return "students/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    @PostMapping
    public String createStudent(@ModelAttribute Student student) {
        service.create(student);
        return getRedirectPath();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", service.findById(id).orElseThrow());
        return "students/form";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) {
        student.setId(id);
        service.update(student);
        return getRedirectPath();
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        service.deleteById(id);
        return getRedirectPath();
    }

}