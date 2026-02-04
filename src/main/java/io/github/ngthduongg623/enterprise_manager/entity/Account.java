package io.github.ngthduongg623.enterprise_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Tài khoản (Account) - Thông tin đăng nhập người dùng
 */
@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @Column(name = "email", length = 100, nullable = false)
    @NotBlank(message = "Email không được để trống")
    private String email;
    
    @Column(name = "password", length = 50, nullable = false)
    @NotBlank(message = "Password không được để trống")
    private String password;
    
    public Account() {
    }
    
    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
