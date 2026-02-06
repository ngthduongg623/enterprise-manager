package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.Account;

/**
 * Quản lý tài khoản (Account Management)
 * UC01 - Đăng nhập
 * UC02 - Quản lý tài khoản
 * UC09 - Đổi mật khẩu
 * UC10 - Đăng xuất
 */
public interface AccountService {
    List<Account> findAll();
    
    Optional<Account> findByEmail(String email);
    
    Account save(Account account);
    
    void deleteByEmail(String email);
    
    boolean existsByEmail(String email);

    /**
     * Change password for an existing account.
     */
    Account changePassword(String email, String newEncodedPassword);
}
