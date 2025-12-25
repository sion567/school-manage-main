package com.company.project.mapper;

import com.company.project.core.mapper.GenericMapper;
import com.company.project.domain.Teacher;
import com.company.project.dto.TeacherCreateDTO;
import com.company.project.dto.TeacherUpdateDTO;
import com.company.project.vo.TeacherSimpleVO;
import com.company.project.vo.TeacherVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeacherMapper extends GenericMapper<Teacher, TeacherVO, TeacherCreateDTO, TeacherUpdateDTO, Long> {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher toEntity(TeacherCreateDTO dto);

    void updateEntityFromDTO(TeacherUpdateDTO dto, @MappingTarget Teacher entity);

    @Mapping(target = "fullName", expression = "java(entity.getFirstName() + \" \" + entity.getLastName())")
    TeacherVO toVO(Teacher entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    List<TeacherSimpleVO> toSimpleVOList(List<Teacher> entityList);
}
