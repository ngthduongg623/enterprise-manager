package io.github.ngthduongg623.enterprise_manager; // Đảm bảo package này khớp với dự án của bạn

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Đánh dấu class này là một Controller xử lý các request REST
public class HelloController {

	// Ánh xạ các HTTP GET request tới đường dẫn "/hello" vào phương thức này
    @GetMapping("/hello")
    public String sayHello() {
		return "Hello, World!";
    }
}