package io.github.ngthduongg623.enterprise_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/showMyLoginPage")
    public String showLoginPage() {
        return "login";
    }
    @GetMapping("/access-denied")
    public String denied(){
        return "denied" ;
    }
}