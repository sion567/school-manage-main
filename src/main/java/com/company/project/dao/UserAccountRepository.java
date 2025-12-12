package com.company.project.dao;

import java.util.Optional;

import com.company.project.core.dao.BaseRepository;
import com.company.project.domain.UserAccount;

import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends BaseRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
    boolean existsByUsername(String username);
}
