package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.Payroll;

/**
 * UC06 - Tính toán lương (Calculate Payroll)
 * UC07 - Xem thông tin tiền lương (View Salary Info)
 */
public interface PayrollService {
    List<Payroll> findAll();
    
    Optional<Payroll> findById(Integer id);
    
    List<Payroll> findByEmployeeId(Integer employeeId);
    
    List<Payroll> findByMonth(String month);
    
    Payroll save(Payroll payroll);
    
    void deleteById(Integer id);
    
    /**
     * UC06 - Calculate payroll for employee in given month
     * @param employeeId employee to calculate for
     * @param month month in format "YYYY-MM"
     * @param bonusAmount bonus amount (thuong)
     * @param fineAmount fine/penalty amount (phat)
     * @return calculated Payroll record
     */
    Payroll calculatePayroll(Integer employeeId, String month, Integer bonusAmount, Integer fineAmount);
}
