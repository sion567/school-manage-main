package com.company.project.mapper;

import com.company.project.core.mapper.GenericMapper;
import com.company.project.domain.Student;
import com.company.project.dto.StudentCreateDTO;
import com.company.project.dto.StudentUpdateDTO;
import com.company.project.vo.StudentVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper extends GenericMapper<Student, StudentVO, StudentCreateDTO, StudentUpdateDTO, Long> {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
}
