package io.github.ngthduongg623.enterprise_manager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ngthduongg623.enterprise_manager.entity.Payroll;
import io.github.ngthduongg623.enterprise_manager.service.PayrollService;

/**
 * UC06 - Tính toán lương (Calculate Payroll) - Admin/HR
 * UC07 - Xem thông tin tiền lương (View Salary Info) - Employee/Admin
 */
@RestController
@RequestMapping("/api/payrolls")
public class PayrollRestController {
    
    private final PayrollService payrollService;
    
    @Autowired
    public PayrollRestController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }
    
    // UC07 - Read-only endpoints for Employee & Admin
    @GetMapping
    public ResponseEntity<List<Payroll>> findAll() {
        List<Payroll> payrolls = payrollService.findAll();
        return ResponseEntity.ok(payrolls);
    }
    
    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<Payroll>> findByEmployee(@PathVariable Integer employeeId) {
        List<Payroll> payrolls = payrollService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(payrolls);
    }
    
    @GetMapping("/by-month/{month}")
    public ResponseEntity<List<Payroll>> findByMonth(@PathVariable String month) {
        List<Payroll> payrolls = payrollService.findByMonth(month);
        return ResponseEntity.ok(payrolls);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Payroll> findById(@PathVariable Integer id) {
        Optional<Payroll> payroll = payrollService.findById(id);
        return payroll.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    /**
     * UC06 - Calculate payroll for employee in given month
     * Parameters: employeeId, month (YYYY-MM), bonusAmount, fineAmount
     */
    @PostMapping("/calculate")
    public ResponseEntity<Payroll> calculatePayroll(
            @RequestParam Integer employeeId,
            @RequestParam String month,
            @RequestParam(required = false, defaultValue = "0") Integer bonusAmount,
            @RequestParam(required = false, defaultValue = "0") Integer fineAmount) {
        Payroll payroll = payrollService.calculatePayroll(employeeId, month, bonusAmount, fineAmount);
        return ResponseEntity.ok(payroll);
    }
}

