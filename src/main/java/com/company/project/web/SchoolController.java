package com.company.project.web;

import com.company.project.core.exception.BusinessException;
import com.company.project.core.exception.ResourceNotFoundException;
import com.company.project.domain.School;
import com.company.project.dto.SchoolCreateDTO;
import com.company.project.dto.SchoolUpdateDTO;
import com.company.project.service.SchoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(SchoolController.BASE_PATH)
@RequiredArgsConstructor
public class SchoolController {
    static final String BASE_PATH = "/ui/schools";

    public static String getRedirectPath() {
        return "redirect:" + BASE_PATH;
    }

    public static String getRedirectPathWithSlash() {
        return "redirect:" + BASE_PATH + "/";
    }

    private final SchoolService service;

    @ModelAttribute("basePath")
    public String getBasePath() {
        return BASE_PATH;
    }
    @ModelAttribute("activePage")
    public String getActivePage() {
        return "schools";
    }

    @GetMapping
    public String listSchools(Model model) {
        model.addAttribute("schools", service.findAll());
        return "schools/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("school", new School());
        return "schools/input";
    }
    @PostMapping
    public String createSchool(@Valid @ModelAttribute SchoolCreateDTO school, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "schools/input";
        }
        service.createSchool(school);
        model.addAttribute("message", "用户信息提交成功！");
        return getRedirectPath();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("school", service.findById(id).orElseThrow());
        return "schools/edit";
    }

    @PostMapping("/{id}")
    public String updateSchool(@PathVariable Long id, @Valid @ModelAttribute SchoolUpdateDTO school, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "schools/edit";
        }
        service.updateSchool(school);
        model.addAttribute("message", "用户信息提交成功！");
        return getRedirectPath();
    }

    @GetMapping("/{id}/delete")
    public String deleteSchool(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return getRedirectPath();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("删除数据失败", e);
        }
    }
}