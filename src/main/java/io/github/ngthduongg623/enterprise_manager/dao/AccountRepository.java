package io.github.ngthduongg623.enterprise_manager.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ngthduongg623.enterprise_manager.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmail(String email);}