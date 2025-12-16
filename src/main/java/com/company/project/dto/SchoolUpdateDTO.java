package com.company.project.dto;

import com.company.project.core.dto.BaseUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SchoolUpdateDTO(
        Long id,
        @NotBlank(message = "用户名不能为空")
        @Size(max = 100, message = "学校名称不能超过100个字符")
        String name,
        @Email(message = "邮箱格式不正确")
        @NotBlank(message = "邮箱不能为空")
        String email, String phoneNumber,
        @Schema(description = "省/直辖市", example = "北京市")
        String province,
        @Schema(description = "街道", example = "建国路")
        String street) implements BaseUpdateDTO<Long> {
    @Override
    public Long getId() {
        return id;
    }

    public static SchoolUpdateDTO fromCore(Long id, SchoolDTO core) {
        return new SchoolUpdateDTO(id, core.name(), core.email(), core.phoneNumber(), core.province() , core.street());
    }
}
