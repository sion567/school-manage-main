package com.company.project.core.mapper;

import com.company.project.core.dto.BaseDTO;
import com.company.project.core.dto.BaseUpdateDTO;
import com.company.project.core.model.BaseEntity;
import com.company.project.core.vo.BaseVO;
import org.mapstruct.MappingTarget;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface GenericMapper<E extends BaseEntity<ID>, V extends BaseVO<ID>, CREATE_DTO extends BaseDTO, UPDATE_DTO extends BaseUpdateDTO<ID>, ID extends Serializable> {
    V toVO(E entity);
    E toEntity(CREATE_DTO createDto);
    void updateEntityFromDto(UPDATE_DTO updateDto, @MappingTarget E entity);
    default List<V> toVOList(List<E> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }
    default List<E> toEntityList(List<CREATE_DTO> createDtos) {
        if (createDtos == null) {
            return Collections.emptyList();
        }
        return createDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}