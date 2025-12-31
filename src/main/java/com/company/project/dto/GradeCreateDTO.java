package com.company.project.dto;

import com.company.project.core.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GradeCreateDTO(
        @NotBlank(message = "名称不能为空")
        @Size(max = 100, message = "名称不能超过100个字符")
        String name, Long schoolId) implements BaseDTO {

    public static GradeCreateDTO empty() {
        return new GradeCreateDTO("", null);
    }
}
