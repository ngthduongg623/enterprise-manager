package io.github.ngthduongg623.enterprise_manager.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.ngthduongg623.enterprise_manager.entity.Account;
import io.github.ngthduongg623.enterprise_manager.service.AccountService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listAccounts(Model model) {
        List<Account> accounts = accountService.findAll();
        model.addAttribute("accounts", accounts);
        return "accounts/list";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "accounts/form";
    }

    @PostMapping("/save")
    public String saveAccount(@Valid @ModelAttribute("account") Account account, 
                              BindingResult bindingResult, 
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // 6. Hệ thống kiểm tra tính hợp lệ của dữ liệu
        if (bindingResult.hasErrors()) {
            return "accounts/form";
        }

        // 6a. Kiểm tra trùng lặp email (nếu là thêm mới)
        if (accountService.existsByEmail(account.getEmail())) {
            model.addAttribute("error", "Email " + account.getEmail() + " đã tồn tại trong hệ thống.");
            return "accounts/form";
        }

        if (!account.getPassword().startsWith("$2a$")) {
             account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        
        // 7. Hệ thống cập nhật dữ liệu và thông báo thành công
        accountService.save(account);
        redirectAttributes.addFlashAttribute("success", "Thêm tài khoản thành công!");
        return "redirect:/accounts";
    }

    @GetMapping("/delete/{email}")
    public String deleteAccount(@PathVariable("email") String email, RedirectAttributes redirectAttributes) {
        try {
            accountService.deleteByEmail(email);
            redirectAttributes.addFlashAttribute("success", "Xóa tài khoản thành công!");
        } catch (Exception e) {
            // 7a. Hệ thống thông báo không được xóa nếu tài khoản đang được sử dụng (hoặc lỗi khác)
            redirectAttributes.addFlashAttribute("error", "Không thể xóa tài khoản. Tài khoản có thể đang được sử dụng.");
        }
        return "redirect:/accounts";
    }
    
    @GetMapping("/password")
    public String showChangePasswordForm() {
        return "accounts/password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal,
                                 Model model) {
        String email = principal.getName();
        
        Optional<Account> accountOpt = accountService.findByEmail(email);
        if (accountOpt.isEmpty() || !passwordEncoder.matches(oldPassword, accountOpt.get().getPassword())) {
            model.addAttribute("error", "Mật khẩu cũ không đúng");
            return "accounts/password";
        }
        
        if (oldPassword.equals(newPassword)) {
            model.addAttribute("error", "Mật khẩu mới không được trùng với mật khẩu cũ");
            return "accounts/password";
        }
        
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp");
            return "accounts/password";
        }

        accountService.changePassword(email, passwordEncoder.encode(newPassword));
        return "redirect:/logout";
    }
}