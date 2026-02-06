package io.github.ngthduongg623.enterprise_manager.security;

import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.ngthduongg623.enterprise_manager.entity.Account;
import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;
import io.github.ngthduongg623.enterprise_manager.service.AccountService;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeDetailService;

@Service
public class AccountAuthenticationService implements UserDetailsService {

    private final AccountService accountService;
    private final EmployeeDetailService employeeDetailService;

    public AccountAuthenticationService(AccountService accountService, EmployeeDetailService employeeDetailService) {
        this.accountService = accountService;
        this.employeeDetailService = employeeDetailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with email: " + username));

        // Fetch role from EmployeeDetail
        String roleName = "EMPLOYEE";
        Optional<EmployeeDetail> employee = employeeDetailService.findByEmail(username);
        if (employee.isPresent() && employee.get().getRole() != null) {
            roleName = employee.get().getRole().toUpperCase();
        }

        // Ensure ROLE_ prefix for Spring Security
        String authority = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .authorities(new SimpleGrantedAuthority(authority))
                .accountExpired(false) // account is not expired
                .accountLocked(false) // account is not locked
                .credentialsExpired(false) // credentials are not expired
                .disabled(false)
                .build();
    }
}