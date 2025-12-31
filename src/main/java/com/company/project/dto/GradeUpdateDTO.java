package com.company.project.dto;

import com.company.project.core.dto.BaseUpdateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GradeUpdateDTO(Long id,
                             @NotBlank(message = "名称不能为空")
                             @Size(max = 100, message = "名称不能超过100个字符")
                             String name, Long schoolId)  implements BaseUpdateDTO<Long> {

    @Override
    public Long getId() {
        return id;
    }
}
