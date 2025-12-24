package com.company.project.core.mapper;

import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class BaseMapperImpl<ENTITY, VO, CREATE_DTO, UPDATE_DTO>
        implements BaseMapper<ENTITY, VO, CREATE_DTO, UPDATE_DTO> {

    @Override
    public List<VO> toVOList(List<ENTITY> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ENTITY> toEntityList(List<CREATE_DTO> createDtos) {
        if (createDtos == null) {
            return Collections.emptyList();
        }
        return createDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
