package com.company.project.service;

import com.company.project.core.service.BaseService;
import com.company.project.dao.RoleRepository;
import com.company.project.domain.Grade;
import com.company.project.domain.Role;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService extends BaseService<Role, Long> {
    private final RoleRepository repository;
    public RoleService(RoleRepository repository, ModelMapper modelMapper) {
        super(repository, Role.class, modelMapper);
        this.repository = repository;
    }
}
