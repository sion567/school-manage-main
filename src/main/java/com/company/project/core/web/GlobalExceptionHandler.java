package com.company.project.core.web;

import java.util.HashMap;
import java.util.Map;

import com.company.project.core.exception.BusinessException;
import com.company.project.core.exception.DuplicateResourceException;
import com.company.project.core.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("参数验证失败 - 请求路径: {}, 参数错误详情: {}",
                request.getDescription(false),
                ex.getBindingResult().getFieldErrors());

        ModelAndView mav = new ModelAndView("error/validation-error");
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        mav.addObject("errors", errors);
        mav.addObject("message", "参数验证失败");
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("timestamp", System.currentTimeMillis());
        return mav;
    }

    /**
     * 处理请求参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex, WebRequest request) {
        log.warn("缺少请求参数: {} - 参数名: {}", request.getDescription(false), ex.getParameterName());

        ModelAndView mav = new ModelAndView("error/missing-parameter");
        mav.addObject("message", "缺少必要参数: " + ex.getParameterName());
        mav.addObject("parameterName", ex.getParameterName());
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("timestamp", System.currentTimeMillis());

        return mav;
    }

    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.warn("参数类型不匹配: {} - 参数名: {} - 期望类型: {}",
                request.getDescription(false), ex.getName(), ex.getRequiredType());

        ModelAndView mav = new ModelAndView("error/type-mismatch");
        mav.addObject("message", "参数类型不匹配");
        mav.addObject("parameterName", ex.getName());
        mav.addObject("expectedType", ex.getRequiredType().getSimpleName());
        mav.addObject("actualValue", ex.getValue());
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("timestamp", System.currentTimeMillis());

        return mav;
    }

    /**
     * 处理请求体格式错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ModelAndView handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        log.error("请求体格式错误: {}", request.getDescription(false), ex);

        ModelAndView mav = new ModelAndView("error/bad-request");
        mav.addObject("message", "请求体格式错误");
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("timestamp", System.currentTimeMillis());

        return mav;
    }

    /**
     * 处理认证异常
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ModelAndView handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        log.warn("认证失败: {}", request.getDescription(false));

        ModelAndView mav = new ModelAndView("error/unauthorized");
        mav.addObject("message", "用户名或密码错误");
        mav.addObject("status", HttpStatus.UNAUTHORIZED.value());
        mav.addObject("timestamp", System.currentTimeMillis());

        return mav;
    }

    /**
     * 处理权限不足异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        log.warn("权限不足: {}", request.getDescription(false));

        ModelAndView mav = new ModelAndView("error/forbidden");
        mav.addObject("message", "权限不足，无法访问此资源");
        mav.addObject("status", HttpStatus.FORBIDDEN.value());
        mav.addObject("timestamp", System.currentTimeMillis());

        return mav;
    }

    // 处理业务异常 - 支持自定义错误码
    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(
            BusinessException ex, WebRequest request) {
        log.warn("业务异常 - 错误码: {}, 消息: {}, 请求路径: {}",
                ex.getErrorCode(),
                ex.getMessage(),
                request.getDescription(false));
        ModelAndView mav = new ModelAndView("error/business-error");
        mav.addObject("message", ex.getMessage());
        mav.addObject("errorCode", ex.getErrorCode());
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("timestamp", System.currentTimeMillis());

        return mav;
    }

    /**
     * 处理404异常
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        log.warn("资源未找到: {} - 资源: {}", request.getDescription(false), ex.getResource());

        ModelAndView mav = new ModelAndView("error/not-found");
        mav.addObject("message", ex.getMessage());
        mav.addObject("resource", ex.getResource());
        mav.addObject("status", HttpStatus.NOT_FOUND.value());
        mav.addObject("timestamp", System.currentTimeMillis());

        return mav;
    }

    /**
     * 处理重复资源异常
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ModelAndView handleDuplicateResource(DuplicateResourceException ex, WebRequest request) {
        log.warn("重复资源异常: {} - 资源: {}", request.getDescription(false), ex.getResource());

        ModelAndView mav = new ModelAndView("error/duplicate-resource");
        mav.addObject("message", ex.getMessage());
        mav.addObject("resource", ex.getResource());
        mav.addObject("status", HttpStatus.CONFLICT.value());
        mav.addObject("timestamp", System.currentTimeMillis());

        return mav;
    }

    // 处理通用异常
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(
            Exception ex, WebRequest request) {
        // 记录严重错误日志
        log.error("系统异常 - 请求路径: {}, 异常类型: {}, 异常信息: {}",
                request.getDescription(false),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ex);

        ModelAndView mav = new ModelAndView("error/general-error");
        mav.addObject("message", "服务器内部错误，请稍后重试");
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.addObject("timestamp", System.currentTimeMillis());
        mav.addObject("exceptionClass", ex.getClass().getSimpleName());
        mav.addObject("exceptionMessage", ex.getMessage());
        return mav;
    }

}
