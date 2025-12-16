package com.company.project.dao;

import java.util.Optional;

import com.company.project.domain.Role;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepositoryImplementation<Role, Long> {
    Optional<Role> findRoleByRoleName(String roleName);
}
