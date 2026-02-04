package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.Account;

/**
 * UC02 - Quản lý tài khoản (Account Management)
 */
public interface AccountService {
    List<Account> findAll();
    
    Optional<Account> findByEmail(String email);
    
    Account save(Account account);
    
    void deleteByEmail(String email);
    
    boolean existsByEmail(String email);
}
