package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.ngthduongg623.enterprise_manager.dao.AccountRepository;
import io.github.ngthduongg623.enterprise_manager.entity.Account;

@Service
public class AccountServiceImpl implements AccountService {
    
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    
    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
    
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
    
    @Override
    public void deleteByEmail(String email) {
        accountRepository.deleteById(email);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsById(email);
    }
}
