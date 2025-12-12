package com.company.project.core.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer ERROR_CODE = 500;
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
    private String requestId;

    // 构造函数
    public Result() {
        this.timestamp = System.currentTimeMillis();
        this.requestId = UUID.randomUUID().toString();
    }

    public Result(Integer code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态工厂方法
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "success", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(ERROR_CODE, message, null);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}
