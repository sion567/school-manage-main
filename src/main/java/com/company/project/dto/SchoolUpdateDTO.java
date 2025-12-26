package com.company.project.dto;

import com.company.project.core.dto.BaseUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Schema(description = "学校更新实体")
public record SchoolUpdateDTO(
        Long id,
        @NotBlank(message = "学校名称不能为空")
        @Size(max = 100, message = "学校名称不能超过100个字符")
        String schoolName,
        @Email(message = "邮箱格式不正确")
        @NotBlank(message = "邮箱不能为空")
        String email,
        String phone,
        @Schema(description = "省/直辖市", example = "湖南省")
        String province,
        @Schema(description = "省/直辖市", example = "长沙市")
        String city,
        @Schema(description = "区/县", example = "开福区")
        String district,
        @Schema(description = "街道", example = "建国路")
        String street,
        @Schema(description = "邮编", example = "410005")
        Integer postcode) implements BaseUpdateDTO<Long> {

        @Override
        public Long getId() {
                return id;
        }
}
