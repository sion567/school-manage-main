package com.company.project.domain;

import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PersonalInfo {
    @Min(value = 6, message = "年龄不能小于6岁")
    @Max(value = 120, message = "年龄不能大于120岁")
    private int age;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_code")
    private Gender gender;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address.country", column = @Column(name = "home_country")),
            @AttributeOverride(name = "address.province", column = @Column(name = "home_province")),
            @AttributeOverride(name = "address.city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "address.district", column = @Column(name = "home_district")),
            @AttributeOverride(name = "address.street", column = @Column(name = "home_street")),
            @AttributeOverride(name = "address.detailAddress", column = @Column(name = "home_detailAddress")),
            @AttributeOverride(name = "address.postalCode", column = @Column(name = "home_postalCode")),
            @AttributeOverride(name = "email", column = @Column(name = "home_email")),
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "home_phone_number"))
    })
    private ContactInfo home = new ContactInfo();


}
