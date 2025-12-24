package com.company.project.core.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface BaseMapper<ENTITY, VO, CREATE_DTO, UPDATE_DTO> {

    VO toVO(ENTITY entity);
    ENTITY toEntity(CREATE_DTO createDto);
    void updateEntityFromDto(UPDATE_DTO updateDto, @MappingTarget ENTITY entity);
    List<VO> toVOList(List<ENTITY> entities);
    List<ENTITY> toEntityList(List<CREATE_DTO> createDtos);
}