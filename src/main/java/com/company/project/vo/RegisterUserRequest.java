package com.company.project.vo;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest (
        @NotNull(message = "姓不能为空")
        @Size(min = 1, max = 10, message = "长度必须在1-10个字符之间")
        String firstName,
        @NotNull(message = "名不能为空")
        @Size(min = 1, max = 10, message = "长度必须在1-10个字符之间")
        String lastName,
        @NotNull(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        String email,
        int type, //0:teacher,1:student
        String password
) {
        public RegisterUserRequest() {
            this("", "","",0,"");
        }
}
