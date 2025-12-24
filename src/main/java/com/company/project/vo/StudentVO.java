package com.company.project.vo;


import com.company.project.core.vo.BaseVO;

public record StudentVO(
        String fullName
) implements BaseVO<Long> {
}
