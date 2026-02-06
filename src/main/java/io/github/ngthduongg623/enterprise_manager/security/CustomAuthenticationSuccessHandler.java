package io.github.ngthduongg623.enterprise_manager.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.github.ngthduongg623.enterprise_manager.entity.Account;
import io.github.ngthduongg623.enterprise_manager.service.AccountService;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AccountService accountService;
    private final EmployeeDetailService employeeDetailService;

    public CustomAuthenticationSuccessHandler(AccountService accountService, EmployeeDetailService employeeDetailService) {
        this.accountService = accountService;
        this.employeeDetailService = employeeDetailService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        Account account = accountService.findByEmail(email)
                .orElse(null);

        // place it in the session
        HttpSession session = request.getSession();
        session.setAttribute("account", account);

        // Also place the employee details in session for UI display
        employeeDetailService.findByEmail(email).ifPresent(employee -> {
            session.setAttribute("currentUser", employee);
        });

        // forward to homepage
        response.sendRedirect(request.getContextPath() + "/");
    }
}