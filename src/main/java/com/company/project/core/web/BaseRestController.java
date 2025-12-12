package com.company.project.core.web;

import java.time.Instant;
import java.util.List;

import com.company.project.core.exception.BusinessException;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BaseRestController{

    public enum ResultCode {
        SUCCESS(200),//成功
        FAIL(400),//失败
        UNAUTHORIZED(401),//未认证（签名错误）
        NOT_FOUND(404),//接口不存在
        INTERNAL_SERVER_ERROR(500);//服务器内部错误

        private final int code;

        ResultCode(int code) {
            this.code = code;
        }

        public int code() {
            return code;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class ResponseResult<T> {
        private int code;
        private String message;
        private T data;
        @Builder.Default
        private Instant timestamp = Instant.now();

        public static <T> ResponseResult<T> success(T data) {
            return ResponseResult.<T>builder().code(ResultCode.SUCCESS.code()).message("success").data(data).build();
        }

        public static <T> ResponseResult<T> success(String message, T data) {
            return ResponseResult.<T>builder().code(ResultCode.SUCCESS.code()).message(message).data(data).build();
        }

        public static <T> ResponseResult<T> error(String message) {
            return ResponseResult.<T>builder().code(ResultCode.INTERNAL_SERVER_ERROR.code()).message(message).data(null).build();
        }

        public static <T> ResponseResult<T> error(int code, String message) {
            return ResponseResult.<T>builder().code(code).message(message).data(null).build();
        }
    }

    protected <T> ResponseEntity<ResponseResult<T>> success(T data) {
        return ResponseEntity.ok(ResponseResult.success(data));
    }

    protected <T> ResponseEntity<ResponseResult<T>> success(String message, T data) {
        return ResponseEntity.ok(ResponseResult.success(message, data));
    }

    protected <T> ResponseEntity<ResponseResult<T>> error(String message) {
        return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseResult.error(message));
    }

    protected <T> ResponseEntity<ResponseResult<T>> error(int code, String message) {
        return ResponseEntity.status(code).body(ResponseResult.error(code, message));
    }

    protected <T> ResponseEntity<ResponseResult<PageResponse<T>>> pageResponse(
            PageRequest pageRequest,
            PageResult<T> pageResult) {

        PageResponse<T> response = new PageResponse<>(
                pageRequest.page(), pageRequest.size(), pageResult.total(),
                (int) Math.ceil((double) pageResult.total() / pageRequest.size()), pageResult.data());

        return ResponseEntity.ok(ResponseResult.success(response));
    }

    protected record PageRequest (
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        public PageRequest(int page, int size) {
            this(page, size, null, null);
        }
        public PageRequest(int page, int size, String sortBy, String sortDirection) {
            if (page <= 0) {
                this.page = 1;
            } else {
                this.page = page;
            }
            if (size <= 0) {
                this.size = 1;
            } else {
                this.size = size;
            }
            if (sortBy == null) {
                this.sortBy = "id";
            } else {
                this.sortBy = sortBy;
            }
            if (sortDirection == null) {
                this.sortDirection = "desc";
            } else {
                this.sortDirection = sortDirection;
            }
        }
    }

    protected record PageResult<T>(
            long total,
            List<T> data
    ) {
    }

    protected record PageResponse<T> (
            int page,
            int size,
            long total,
            int totalPages,
            List<T> data
    ) {
    }

    protected void validateNotNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 验证字符串是否为空
     */
    protected void validateNotBlank(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new BusinessException(message);
        }
    }

    /**
     * 验证数字是否为正数
     */
    protected void validatePositive(Long number, String message) {
        if (number == null || number <= 0) {
            throw new BusinessException(message);
        }
    }
}