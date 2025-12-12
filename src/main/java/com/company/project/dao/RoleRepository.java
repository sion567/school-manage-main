package com.company.project.dao;

import java.util.Optional;

import com.company.project.core.dao.BaseRepository;
import com.company.project.domain.Role;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(String roleName);
}
