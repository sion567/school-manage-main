package com.company.project.dao;

import java.util.Optional;

import com.company.project.domain.UserAccount;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepositoryImplementation<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
    boolean existsByUsername(String username);
}
