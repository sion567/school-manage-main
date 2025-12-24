package com.company.project.service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.company.project.core.exception.BusinessException;
import com.company.project.core.service.BaseService;
import com.company.project.dao.RoleRepository;
import com.company.project.dao.UserAccountRepository;
import com.company.project.domain.*;
import com.company.project.dto.UserAccountDetails;
import com.company.project.vo.RegisterUserRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountService implements UserDetailsService {
    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public void validateUser(RegisterUserRequest req) {
        // 业务逻辑验证
        if (req.email() != null && req.email().contains("@test.com")) {
            throw new BusinessException("测试邮箱不允许注册");
        }
        if (req.email() != null && req.email().toLowerCase().contains("admin")) {
            throw new BusinessException("用户名不能包含admin关键字");
        }
        //...
    }

    public UserAccount createUserAccount(RegisterUserRequest req) {
        UserAccount userAccount = new UserAccount(req.email(), passwordEncoder.encode(req.password()))
                .addRoles(Collections.singleton(createNewRole(req.type())));
//                .addPerson(p);
        Optional<UserAccount> userAccountByUsername = repository
                .findByUsername(userAccount.getUsername());
        if (userAccountByUsername.isEmpty()) {
            return repository.save(userAccount);
        }
        return userAccountByUsername
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("user account not found"));
    }

    public UserAccount createUserAccount(User u) {
        UserAccount userAccount = new UserAccount(u.getEmail(), passwordEncoder.encode(u.getPhoneNumber()))
                .addRoles(Collections.singleton(createNewRole(0))) //TODO
                .addUser(u);
        Optional<UserAccount> userAccountByUsername = repository
                .findByUsername(userAccount.getUsername());
        if (userAccountByUsername.isEmpty()) {
            return repository.save(userAccount);
        }
        return userAccountByUsername
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("user account not found"));
    }

    private Role createNewRole(int type) {
        if (type == 0) {
            Role role = new Role("TEACHER")
                    .addAuthorities(Set.of(
                            new Authority("person:read"),
                            new Authority("person:write")));

            return roleRepository.save(role);
        } else {
            Role role = new Role("STUDENT")
                    .addAuthorities(Set.of(
                            new Authority("person:read"),
                            new Authority("person:write")));

            return roleRepository.save(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = repository
                .findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return new UserAccountDetails(userAccount);
    }
}
