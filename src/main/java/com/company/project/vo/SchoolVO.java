package com.company.project.vo;

import com.company.project.core.vo.BaseVO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public record SchoolVO (
        Long id, String name, String email, String phone, String province, String street) implements BaseVO<Long> {
}
