package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.Payroll;

/**
 * Quản lý lương, chấm công, giờ làm thêm và thống kê:
 * Tính toán lương;
 * Xem thông tin lương;
 * Xem thống kê, báo cáo;
 */
public interface PayrollService {
    
    List<Payroll> findAll();

    
    Optional<Payroll> findById(Integer id);

    
    List<Payroll> findByEmployeeId(Integer employeeId);

    
    List<Payroll> findByMonth(String month);

    
    Optional<Payroll> findByEmployeeIdAndMonth(Integer employeeId, String month);
    
    Payroll save(Payroll payroll);

    
    void deleteById(Integer id);

    
    /**
     * Calculate payroll for a given employee and month.
     * UC06 - Tính toán lương cho một nhân viên trong tháng
     */
    Payroll calculatePayroll(Integer employeeId, String month, Integer bonusAmount, Integer finePerLate, Integer deduction, Double insuranceRate);
    Payroll calculatePayroll(Integer employeeId, String month);
    
    /**
     * UC06 - Tính toán lương cho toàn bộ nhân viên trong tháng
     */
    void calculateMonthlyPayroll(String month, Integer finePerLate, Integer deduction, Double insuranceRate);
    
    /**
     * UC08 - Tổng hợp lương theo tháng
     */
    long getTotalPayrollByMonth(String month);
}
