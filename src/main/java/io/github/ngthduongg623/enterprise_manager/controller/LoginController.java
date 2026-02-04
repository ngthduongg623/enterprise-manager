package io.github.ngthduongg623.enterprise_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.ngthduongg623.enterprise_manager.entity.User;
import io.github.ngthduongg623.enterprise_manager.service.UserService;

/**
 * UC01 - Đăng nhập (Login)
 * UC09 - Đổi mật khẩu (Change Password)
 * UC10 - Đăng xuất (Logout)
 */
@Controller
public class LoginController {
    
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    public LoginController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/showMyLoginPage")
    public String showLoginPage() {
        return "login";
    }
    
    @GetMapping("/access-denied")
    public String denied(){
        return "denied";
    }
    
    /**
     * UC09 - Đổi mật khẩu (Change Password)
     */
    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/showMyLoginPage";
        }
        model.addAttribute("username", authentication.getName());
        return "auth/change-password";
    }
    
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                  @RequestParam String newPassword,
                                  @RequestParam String confirmPassword,
                                  Authentication authentication,
                                  Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/showMyLoginPage";
        }
        
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "auth/change-password";
        }
        
        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("error", "Current password is incorrect");
            model.addAttribute("username", username);
            return "auth/change-password";
        }
        
        // Check new password confirmation
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match");
            model.addAttribute("username", username);
            return "auth/change-password";
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.update(user);
        
        model.addAttribute("success", "Password changed successfully");
        return "auth/change-password";
    }
    
    /**
     * UC10 - Đăng xuất (Logout)
     * Spring Security handles actual logout via /logout endpoint
     */
    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "auth/logout-success";
    }
}