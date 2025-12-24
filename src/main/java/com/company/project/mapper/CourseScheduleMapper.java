package com.company.project.mapper;

import com.company.project.core.mapper.BaseMapper;
import com.company.project.domain.CourseSchedule;
import com.company.project.dto.CourseScheduleCreateDTO;
import com.company.project.dto.CourseScheduleUpdateDTO;
import com.company.project.vo.CourseScheduleVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseScheduleMapper extends BaseMapper<CourseSchedule, CourseScheduleVO, CourseScheduleCreateDTO, CourseScheduleUpdateDTO> {
    CourseScheduleMapper INSTANCE = Mappers.getMapper(CourseScheduleMapper.class);

    CourseSchedule toEntity(CourseScheduleCreateDTO dto);

    void updateEntityFromDTO(CourseScheduleUpdateDTO dto, @MappingTarget CourseSchedule entity);

    CourseScheduleVO toVO(CourseSchedule entity);
}
