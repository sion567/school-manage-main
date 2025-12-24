package com.company.project.web;

import java.util.List;

import com.company.project.core.web.CrudController;
import com.company.project.domain.*;
import com.company.project.dto.ClassroomCreateDTO;
import com.company.project.dto.ClassroomUpdateDTO;
import com.company.project.dto.CourseCreateDTO;
import com.company.project.dto.CourseUpdateDTO;
import com.company.project.service.*;

import com.company.project.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(ClassroomController.BASE_PATH)
public class ClassroomController extends CrudController<Classroom, ClassroomVO, ClassroomCreateDTO, ClassroomUpdateDTO, Long> {
    public static final String BASE_PATH = "/ui/classrooms";
    static final String LIST_VIEW = "classrooms/list";
    static final String INPUT_VIEW = "classrooms/input";
    static final String EDIT_VIEW = "classrooms/edit";


    private final ClassroomService service;
    private final GradeService gradeService;
    private final TeacherService teacherService;
    private final SchoolService schoolService;

    public ClassroomController(ClassroomService service, GradeService gradeService, TeacherService teacherService, SchoolService schoolService) {
        super(service, BASE_PATH, LIST_VIEW, INPUT_VIEW, EDIT_VIEW);
        this.service = service;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
        this.schoolService = schoolService;
    }

    @ModelAttribute("grades")
    public Iterable<GradeSimpleVO> grades() {
        return gradeService.findAllSimple();
    }
    @ModelAttribute("teachers")
    public Iterable<TeacherSimpleVO> teachers() {
        return teacherService.findAllSimple();
    }

    @ModelAttribute("activePage")
    public String getActivePage() {
        return "classrooms";
    }

    @GetMapping
    public String list(
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

    @Override
    protected ClassroomCreateDTO createNewDto() {
        return null;
    }

    @GetMapping("/school-select")
    public String schoolSelectWithParams(
            @RequestParam(value = "schoolId", required = false) Long schoolId,
            Model model) {
        model.addAttribute("classroom", new Classroom());
        List<SchoolVO> schools = schoolService.findAll();
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
}