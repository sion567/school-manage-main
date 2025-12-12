package com.company.project.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.company.project.core.model.DateAuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sys_role")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role extends DateAuditEntity {

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    private Set<Authority> authorities = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId")
    @JsonIgnore
    private UserAccount userAccount;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role addAuthorities(Set<Authority> newAuthorities) {
        this.authorities.addAll(newAuthorities);
        for (Authority authority : newAuthorities) {
            if (authority != null) {
                authority.setRole(this);
            }
        }
        return this;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> grantedAuthorities = this.authorities
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(
                new SimpleGrantedAuthority("ROLE_" + this.roleName));
        return grantedAuthorities;
    }
}

