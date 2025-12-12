package com.company.project.domain;

public enum Gender {
    MALE("M", "男", "Male"),
    FEMALE("F", "女", "Female");

    private final String code;
    private final String zhDescription;
    private final String enDescription;

    Gender(String code, String zhDescription, String enDescription) {
        this.code = code;
        this.zhDescription = zhDescription;
        this.enDescription = enDescription;
    }

    public String getDescription() {
        return zhDescription;
    }

    public String getEnglishDescription() {
        return enDescription;
    }

    public String getCode() {
        return code;
    }

    // 根据code获取枚举
    public static Gender fromCode(String code) {
        for (Gender gender : Gender.values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("未知的性别代码: " + code);
    }

    // 根据描述获取枚举
    public static Gender fromDescription(String description) {
        for (Gender gender : Gender.values()) {
            if (gender.getDescription().equals(description)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("未知的性别描述: " + description);
    }
}
