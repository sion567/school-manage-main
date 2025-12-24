package com.company.project.mapper;

import com.company.project.core.mapper.BaseMapper;

import com.company.project.domain.Classroom;
import com.company.project.domain.School;
import com.company.project.dto.ClassroomCreateDTO;
import com.company.project.dto.ClassroomUpdateDTO;
import com.company.project.vo.ClassroomSimpleVO;
import com.company.project.vo.ClassroomVO;
import com.company.project.vo.SchoolSimpleVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClassroomMapper extends BaseMapper<Classroom, ClassroomVO, ClassroomCreateDTO, ClassroomUpdateDTO> {

    ClassroomMapper INSTANCE = Mappers.getMapper(ClassroomMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    List<ClassroomSimpleVO> toSimpleVoList(List<Classroom> entityList);
}
