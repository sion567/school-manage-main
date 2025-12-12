package com.company.project.vo;

public record ClassroomDetailRecord (
    Long id,
    String name,
    String gradeName,
    String teacherFirstName,
    String teacherLastName
) {
    public String getTeacherName() {
        return this.teacherFirstName + ' ' + this.teacherLastName;
    }
}
