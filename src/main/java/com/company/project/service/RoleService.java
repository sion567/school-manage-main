package com.company.project.service;

import com.company.project.core.service.BaseService;
import com.company.project.dao.RoleRepository;
import com.company.project.domain.Role;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {
    private final RoleRepository repository;
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }
}
