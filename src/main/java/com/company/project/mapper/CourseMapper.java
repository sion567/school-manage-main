package com.company.project.mapper;

import com.company.project.core.mapper.GenericMapper;
import com.company.project.domain.Course;
import com.company.project.dto.CourseCreateDTO;
import com.company.project.dto.CourseUpdateDTO;
import com.company.project.vo.CourseVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper extends GenericMapper<Course, CourseVO, CourseCreateDTO, CourseUpdateDTO, Long> {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
}
