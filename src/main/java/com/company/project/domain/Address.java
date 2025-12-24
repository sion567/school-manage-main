package com.company.project.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Schema(description = "详细地址对象")
public class Address {
    @Schema(description = "国家", example = "中国")
    private String country;
    @Schema(description = "省", example = "湖南省")
    private String province;
    @Schema(description = "市", example = "湘潭韶山市")
    private String city;
    @Schema(description = "区/县", example = "韶山乡")
    private String district;
    @Schema(description = "街道", example = "故园路")
    private String street;
    @Schema(description = "详细地址", example = "88号大厦")
    private String detailAddress;
    @Schema(description = "邮政编码", example = "100000")
    private String postalCode;

//    latitude DECIMAL(10, 6) COMMENT '纬度',
//    longitude DECIMAL(10, 6) COMMENT '经度',
}
