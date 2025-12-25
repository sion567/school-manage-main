package com.company.project.core.web;


import com.company.project.core.exception.BusinessException;
import com.company.project.core.exception.ResourceNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController {
    protected static final String REDIRECT_PREFIX = "redirect:";
    protected static final String MESSAGE_ATTRIBUTE = "message";
    protected static final String ERRORS_ATTRIBUTE = "errors";

    public static String getRedirectPath(String basePath) {
        return REDIRECT_PREFIX + basePath;
    }

    public static String getRedirectPathWithSlash(String basePath) {
        return REDIRECT_PREFIX + basePath + "/";
    }

    // 通用的保存成功处理
    protected String handleSaveSuccess(Model model, String message, String basePath) {
        model.addAttribute(MESSAGE_ATTRIBUTE, message);
        return getRedirectPath(basePath);
    }

    // 通用的保存失败处理
    protected String handleSaveError(Model model, BindingResult bindingResult, String viewName) {
        model.addAttribute(ERRORS_ATTRIBUTE, bindingResult.getFieldErrors());
        return viewName;
    }

    // 通用的删除成功处理
    protected String handleDeleteSuccess(String basePath) {
        return getRedirectPath(basePath);
    }

    public abstract String getBasePath();
    public abstract String getActivePage();
}
