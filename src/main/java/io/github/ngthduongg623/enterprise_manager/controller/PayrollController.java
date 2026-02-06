package io.github.ngthduongg623.enterprise_manager.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;
import io.github.ngthduongg623.enterprise_manager.entity.Payroll;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeDetailService;
import io.github.ngthduongg623.enterprise_manager.service.PayrollService;

@Controller
@RequestMapping("/payroll")
public class PayrollController {

    private final PayrollService payrollService;
    private final EmployeeDetailService employeeService;

    @Autowired
    public PayrollController(PayrollService payrollService, EmployeeDetailService employeeService) {
        this.payrollService = payrollService;
        this.employeeService = employeeService;
    }

    @GetMapping("/manage")
    public String managePayroll(Model model) {
        List<Payroll> payrolls = payrollService.findAll();
        model.addAttribute("payrolls", payrolls);
        long totalPayroll = payrolls.stream().mapToLong(Payroll::getTotalSalary).sum();
        model.addAttribute("totalPayroll", totalPayroll);
        return "payroll/manage";
    }

    @GetMapping("/calculate")
    public String showCalculateForm() {
        return "payroll/calculate";
    }

    @PostMapping("/calculate")
    public String calculatePayroll(@RequestParam("month") String month,
                                   @RequestParam(value = "fine", defaultValue = "0") Integer fine,
                                   @RequestParam(value = "deduction", defaultValue = "0") Integer deduction,
                                   @RequestParam(value = "insuranceRate", defaultValue = "10.5") Double insuranceRate) {
        payrollService.calculateMonthlyPayroll(month, fine, deduction, insuranceRate);
        return "redirect:/payroll/manage";
    }

    @GetMapping("/my-history")
    public String myHistory(Model model, Principal principal) {
        String email = principal.getName();
        Optional<EmployeeDetail> employee = employeeService.findByEmail(email);
        
        if (employee.isPresent()) {
            List<Payroll> myPayrolls = payrollService.findByEmployeeId(employee.get().getEmployeeId());
            model.addAttribute("myPayrolls", myPayrolls);
            return "payroll/my-history";
        }
        
        return "redirect:/";
    }
}