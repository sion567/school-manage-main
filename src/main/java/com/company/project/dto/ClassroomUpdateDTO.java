package com.company.project.dto;

import com.company.project.core.dto.BaseUpdateDTO;

public record ClassroomUpdateDTO(Long id)  implements BaseUpdateDTO<Long> {

    @Override
    public Long getId() {
        return id;
    }
}