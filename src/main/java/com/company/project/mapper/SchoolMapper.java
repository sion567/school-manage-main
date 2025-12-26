package com.company.project.mapper;

import com.company.project.core.mapper.GenericMapper;
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
public interface SchoolMapper extends GenericMapper<School, SchoolVO, SchoolCreateDTO, SchoolUpdateDTO, Long> {

    SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "schoolName")
    @Mapping(target = "contactInfo", expression = "java(createContactInfo(dto))")
//    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
//    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    School toEntity(SchoolCreateDTO dto);

    @Mapping(target = "id")
    @Mapping(target = "schoolName", source = "name")
    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phone", source = "contactInfo.phoneNumber")
    @Mapping(target = "province", source = "contactInfo.address.province")
    @Mapping(target = "city", source = "contactInfo.address.city")
    @Mapping(target = "district", source = "contactInfo.address.district")
    @Mapping(target = "street", source = "contactInfo.address.street")
    @Mapping(target = "postcode", source = "contactInfo.address.postalCode")
    SchoolUpdateDTO toDTO(School entity);

    @Mapping(target = "id")
    @Mapping(target = "name", source = "schoolName")
    @Mapping(target = "contactInfo.email", source = "email")
    @Mapping(target = "contactInfo.phoneNumber", source = "phone")
    @Mapping(target = "contactInfo.address.province", source = "province")
    @Mapping(target = "contactInfo.address.city", source = "city")
    @Mapping(target = "contactInfo.address.district", source = "district")
    @Mapping(target = "contactInfo.address.street", source = "street")
    @Mapping(target = "contactInfo.address.postalCode", source = "postcode")
    void updateEntityFromDTO(SchoolUpdateDTO dto, @MappingTarget School entity);

    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phone", source = "contactInfo.phoneNumber")
    @Mapping(target = "address", expression = "java(toAddress(entity))")
    @Mapping(target = "postcode", source = "contactInfo.address.postalCode")
    SchoolVO toVO(School entity);

    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phone", source = "contactInfo.phoneNumber")
    @Mapping(target = "address", expression = "java(toAddress(entity))")
    @Mapping(target = "postcode", source = "contactInfo.address.postalCode")
    List<SchoolVO> toVOList(List<School> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    List<SchoolSimpleVO> toSimpleVOList(List<School> entityList);

    default String toAddress(School entity) {
        return entity.getContactInfo().getAddress().getProvince() + " " +
                entity.getContactInfo().getAddress().getCity() + " " +
                entity.getContactInfo().getAddress().getDistrict() + " " +
                entity.getContactInfo().getAddress().getStreet();
    }

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
                if (dto.city() != null) {
                    address.setCity(dto.province());
                }
                if (dto.district() != null) {
                    address.setDistrict(dto.province());
                }
                if (dto.street() != null) {
                    address.setStreet(dto.street());
                }
                if (dto.postcode() != null) {
                    address.setPostalCode(dto.postcode());
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
