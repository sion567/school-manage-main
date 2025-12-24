package com.company.project.web;

import com.company.project.service.UserAccountService;
import com.company.project.vo.RegisterUserRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountService service;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "用户名或密码错误");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "您已成功退出");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("req", new RegisterUserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterUserRequest req,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            // 2. 业务逻辑验证
            service.validateUser(req);
            // 3. 保存用户
            service.createUserAccount(req);
            // 4. 成功跳转到成功页面
            model.addAttribute("message", "用户注册成功！");
            return "redirect:/login?success=true";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }


    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        log.debug("go to dashboard");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("name", username);
            model.addAttribute("message", "欢迎访问仪表板");
            model.addAttribute("activePage", "dashboard");
        }
        return "dashboard";
    }
}
