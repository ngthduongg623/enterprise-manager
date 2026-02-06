package io.github.ngthduongg623.enterprise_manager.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.github.ngthduongg623.enterprise_manager.entity.Account;
import io.github.ngthduongg623.enterprise_manager.entity.Department;
import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;
import io.github.ngthduongg623.enterprise_manager.service.AccountService;
import io.github.ngthduongg623.enterprise_manager.service.DepartmentService;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeDetailService;

@Component
public class DataLoader implements CommandLineRunner {

    private final AccountService accountService;
    private final EmployeeDetailService employeeDetailService;
    private final DepartmentService departmentService;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(AccountService accountService, 
                      EmployeeDetailService employeeDetailService,
                      DepartmentService departmentService,
                      PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.employeeDetailService = employeeDetailService;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Ensure a department exists
        Department itDept = departmentService.findByName("IT");
        if (itDept == null) {
            itDept = new Department();
            itDept.setName("IT");
            itDept = departmentService.save(itDept);
        }

        // Create default admin account
        String adminEmail = "admin@example.com";
        if (!accountService.existsByEmail(adminEmail)) {
            Account admin = new Account();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123"));
            accountService.save(admin);
            System.out.println("Created default admin account: " + adminEmail);
        }

        // Check and create EmployeeDetail independently
        if (employeeDetailService.findByEmail(adminEmail).isEmpty()) {
            EmployeeDetail adminEmp = new EmployeeDetail();
            adminEmp.setEmail(adminEmail);
            adminEmp.setName("Administrator");
            adminEmp.setRole("ADMIN");
            adminEmp.setDepartment(itDept);
            adminEmp.setJoinDate(LocalDate.now());
            adminEmp.setGender("Nam");
            adminEmp.setNumberphone("0912345678");
            adminEmp.setAddress("Hà Nội");
            employeeDetailService.save(adminEmp);
            System.out.println("Created default admin employee detail: " + adminEmail);
        }

        // Create some default employee accounts for testing
        String emp1Email = "employee1@example.com";
        if (!accountService.existsByEmail(emp1Email)) {
            Account emp1 = new Account();
            emp1.setEmail(emp1Email);
            emp1.setPassword(passwordEncoder.encode("password1"));
            accountService.save(emp1);
            System.out.println("Created default employee account: " + emp1Email);
        }

        if (employeeDetailService.findByEmail(emp1Email).isEmpty()) {
            EmployeeDetail emp1Detail = new EmployeeDetail();
            emp1Detail.setEmail(emp1Email);
            emp1Detail.setName("Employee One");
            emp1Detail.setRole("EMPLOYEE");
            emp1Detail.setDepartment(itDept);
            emp1Detail.setJoinDate(LocalDate.now());
            employeeDetailService.save(emp1Detail);
            System.out.println("Created default employee detail: " + emp1Email);
        }

        String emp2Email = "employee2@example.com";
        if (!accountService.existsByEmail(emp2Email)) {
            Account emp2 = new Account();
            emp2.setEmail(emp2Email);
            emp2.setPassword(passwordEncoder.encode("password2"));
            accountService.save(emp2);
            System.out.println("Created default employee account: " + emp2Email);
        }

        if (employeeDetailService.findByEmail(emp2Email).isEmpty()) {
            EmployeeDetail emp2Detail = new EmployeeDetail();
            emp2Detail.setEmail(emp2Email);
            emp2Detail.setName("Employee Two");
            emp2Detail.setRole("EMPLOYEE");
            emp2Detail.setDepartment(itDept);
            emp2Detail.setJoinDate(LocalDate.now());
            employeeDetailService.save(emp2Detail);
            System.out.println("Created default employee detail: " + emp2Email);
        }
    }
}