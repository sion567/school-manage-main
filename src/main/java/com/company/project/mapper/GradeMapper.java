package com.company.project.mapper;

import com.company.project.core.mapper.GenericMapper;
import com.company.project.domain.Grade;
import com.company.project.dto.GradeCreateDTO;
import com.company.project.dto.GradeUpdateDTO;
import com.company.project.vo.GradeSimpleVO;
import com.company.project.vo.GradeVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GradeMapper extends GenericMapper<Grade, GradeVO, GradeCreateDTO, GradeUpdateDTO, Long> {

    GradeMapper INSTANCE = Mappers.getMapper(GradeMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    List<GradeSimpleVO> toSimpleVOList(List<Grade> entityList);
}
