package com.company.project.domain;

import com.company.project.core.model.DateAuditEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "sys_user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends DateAuditEntity {
    @NotBlank
    @Size(min = 1, max = 10, message = "姓长度必须在1-10个字符之间")
    @Schema(description = "姓名", example = "朱")
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 10, message = "名长度必须在1-10个字符之间")
    @Schema(description = "名", example = "七七")
    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserAccount userAccount;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
