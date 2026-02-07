package io.github.ngthduongg623.enterprise_manager.controller;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.ngthduongg623.enterprise_manager.entity.Department;
import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;
import io.github.ngthduongg623.enterprise_manager.entity.Payroll;
import io.github.ngthduongg623.enterprise_manager.service.DepartmentService;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeDetailService;
import io.github.ngthduongg623.enterprise_manager.service.PayrollService;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final PayrollService payrollService;
    private final DepartmentService departmentService;
    private final EmployeeDetailService employeeService;

    public ReportController(PayrollService payrollService, DepartmentService departmentService, EmployeeDetailService employeeService) {
        this.payrollService = payrollService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String showReports(@RequestParam(value = "month", required = false) String month, Model model) {
        if (month == null || month.isEmpty()) {
            month = YearMonth.now().toString();
        }

        List<Payroll> payrolls = payrollService.findByMonth(month);
        long totalPayroll = payrollService.getTotalPayrollByMonth(month);

        model.addAttribute("payrolls", payrolls);
        model.addAttribute("totalPayroll", totalPayroll);
        model.addAttribute("selectedMonth", month);

        return "reports/index";
    }

    @GetMapping("/department")
    public String showDepartmentStats(Model model) {
        List<Department> departments = departmentService.findAll();
        List<EmployeeDetail> employees = employeeService.findAll();

        Map<String, Long> stats = new HashMap<>();
        // Khởi tạo bộ đếm cho tất cả phòng ban
        for (Department dept : departments) {
            stats.put(dept.getName(), 0L);
        }

        // Đếm nhân viên
        for (EmployeeDetail emp : employees) {
            if (emp.getDepartment() != null) {
                String deptName = emp.getDepartment().getName();
                stats.put(deptName, stats.getOrDefault(deptName, 0L) + 1);
            }
        }

        model.addAttribute("departmentStats", stats);
        model.addAttribute("totalEmployees", employees.size());
        return "reports/department";
    }

    @PostMapping("/calculate")
    public String calculatePayroll(@RequestParam("month") String month,
                                   @RequestParam(value = "finePerLate", defaultValue = "50000") Integer finePerLate,
                                   @RequestParam(value = "deduction", defaultValue = "11000000") Integer deduction,
                                   @RequestParam(value = "insuranceRate", defaultValue = "10.5") Double insuranceRate) {
        payrollService.calculateMonthlyPayroll(month, finePerLate, deduction, insuranceRate);
        return "redirect:/reports?month=" + month;
    }
}