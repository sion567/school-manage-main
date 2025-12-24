package com.company.project.mapper;

import com.company.project.core.mapper.BaseMapper;
import com.company.project.domain.Address;
import com.company.project.domain.ContactInfo;
import com.company.project.domain.School;
import com.company.project.dto.SchoolCreateDTO;
import com.company.project.dto.SchoolUpdateDTO;
import com.company.project.vo.SchoolSimpleVO;
import com.company.project.vo.SchoolVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SchoolMapper extends BaseMapper<School, SchoolVO, SchoolCreateDTO, SchoolUpdateDTO> {

    SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contactInfo", expression = "java(createContactInfo(dto))")
//    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
//    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    School toEntity(SchoolCreateDTO dto);

    @Mapping(target = "id", ignore = false)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "contactInfo.email", source = "email")
    @Mapping(target = "contactInfo.phoneNumber", source = "phone")
    @Mapping(target = "contactInfo.address.province", source = "province")
    @Mapping(target = "contactInfo.address.street", source = "street")
    void updateEntityFromDTO(SchoolUpdateDTO dto, @MappingTarget School entity);

    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phone", source = "contactInfo.phoneNumber")
    @Mapping(target = "province", source = "contactInfo.address.province")
    @Mapping(target = "street", source = "contactInfo.address.street")
    SchoolVO toVO(School entity);

    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phone", source = "contactInfo.phoneNumber")
    @Mapping(target = "province", source = "contactInfo.address.province")
    @Mapping(target = "street", source = "contactInfo.address.street")
    List<SchoolVO> toVOList(List<School> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    List<SchoolSimpleVO> toSimpleVoList(List<School> entityList);

    default ContactInfo createContactInfo(SchoolCreateDTO dto) {
        if (dto.email() != null || dto.phone() != null ||
                dto.province() != null || dto.street() != null) {

            ContactInfo contactInfo = new ContactInfo();

            if (dto.email() != null) {
                contactInfo.setEmail(dto.email());
            }
            if (dto.phone() != null) {
                contactInfo.setPhoneNumber(dto.phone());
            }

            if (dto.province() != null || dto.street() != null) {
                Address address = new Address();
                if (dto.province() != null) {
                    address.setProvince(dto.province());
                }
                if (dto.street() != null) {
                    address.setStreet(dto.street());
                }
                contactInfo.setAddress(address);
            }

            return contactInfo;
        }
        return null;
    }


    default String wrapper(String val) {
        return val != null ? val : "";
    }
}
