package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.Account;

/**
 * Quản lý tài khoản:
 * Đăng nhập
 * Quản lý tài khoản
 * Đổi mật khẩu
 * Đăng xuất
 */
public interface AccountService {
    List<Account> findAll();
    
    Optional<Account> findByEmail(String email);
    
    Account save(Account account);
    
    void deleteByEmail(String email);
    
    boolean existsByEmail(String email);

    /**
     * Đổi mật khẩu cho tài khoản dựa trên email
     * @param email
     * @param newEncodedPassword
     * @return
     */
    Account changePassword(String email, String newEncodedPassword);
}
