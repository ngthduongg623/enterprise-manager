package io.github.ngthduongg623.enterprise_manager.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.ngthduongg623.enterprise_manager.entity.User;
import io.github.ngthduongg623.enterprise_manager.security.WebUser;
import io.github.ngthduongg623.enterprise_manager.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listAccounts(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "list-accounts";
    }

    @GetMapping("/showCreateForm")
    public String showCreateForm(Model model) {
        model.addAttribute("webUser", new WebUser());
        model.addAttribute("roles", List.of("ROLE_EMPLOYEE", "ROLE_ADMIN"));
        return "account-form";
    }

    @PostMapping("/save")
    public String saveAccount(@ModelAttribute("webUser") @Valid WebUser webUser, BindingResult bindingResult,
                              @RequestParam(name = "roleName", required = false) String roleName,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", List.of("ROLE_EMPLOYEE", "ROLE_ADMIN"));
            return "account-form";
        }
        if (roleName == null || roleName.isBlank()) roleName = "ROLE_EMPLOYEE";
        userService.createUser(webUser, roleName);
        return "redirect:/accounts/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/accounts/list";
    }
}
