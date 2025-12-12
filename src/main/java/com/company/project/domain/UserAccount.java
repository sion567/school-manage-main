package com.company.project.domain;

import java.util.HashSet;
import java.util.Set;

import com.company.project.core.model.DateAuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user_account")
public class UserAccount extends DateAuditEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userAccount", cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    private Set<Role> roles = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User person;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAccountNonLocked = true;
        this.isAccountNonExpired = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }

    public UserAccount addRoles(Set<Role> newRoles) {
        this.roles.addAll(newRoles);
        for (Role role : newRoles) {
            if (role != null) {
                role.setUserAccount(this);
            }
        }
        return this;
    }

    public UserAccount addUser(User u) {
        if (u != null) {
            this.person = u;
            u.setUserAccount(this);
        }
        return this;
    }
}