package io.github.ngthduongg623.enterprise_manager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ngthduongg623.enterprise_manager.entity.Account;
import io.github.ngthduongg623.enterprise_manager.service.AccountService;

/**
 * UC02 - Quản lý tài khoản (Account Management)
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {
    
    private final AccountService accountService;
    
    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    // CRUD endpoints for Admin
    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<Account> findByEmail(@PathVariable String email) {
        Optional<Account> account = accountService.findByEmail(email);
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        if (accountService.existsByEmail(account.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Account created = accountService.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{email}")
    public ResponseEntity<Account> updateAccount(@PathVariable String email, @RequestBody Account account) {
        Optional<Account> existing = accountService.findByEmail(email);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        account.setEmail(email);
        Account updated = accountService.save(account);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String email) {
        Optional<Account> account = accountService.findByEmail(email);
        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        accountService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
