package io.github.ngthduongg623.enterprise_manager; // Đảm bảo package này khớp với dự án của bạn

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}

