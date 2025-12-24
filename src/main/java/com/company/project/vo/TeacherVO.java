package com.company.project.vo;

import com.company.project.core.vo.BaseVO;

public record TeacherVO(
        String fullName
) implements BaseVO<Long> {}