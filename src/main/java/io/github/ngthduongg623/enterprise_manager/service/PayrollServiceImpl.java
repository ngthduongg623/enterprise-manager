package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.ngthduongg623.enterprise_manager.dao.AttendanceRepository;
import io.github.ngthduongg623.enterprise_manager.dao.PayrollRepository;
import io.github.ngthduongg623.enterprise_manager.entity.Attendance;
import io.github.ngthduongg623.enterprise_manager.entity.Payroll;

@Service
public class PayrollServiceImpl implements PayrollService {
    
    private final PayrollRepository payrollRepository;
    private final AttendanceRepository attendanceRepository;
    
    // Configurable salary constants (in VND)
    private static final int DAILY_BASE_SALARY = 1000000; // Base salary per day
    private static final double TAX_RATE = 0.10; // 10% tax
    private static final double INSURANCE_RATE = 0.08; // 8% health insurance
    
    @Autowired
    public PayrollServiceImpl(PayrollRepository payrollRepository, AttendanceRepository attendanceRepository) {
        this.payrollRepository = payrollRepository;
        this.attendanceRepository = attendanceRepository;
    }
    
    @Override
    public List<Payroll> findAll() {
        return payrollRepository.findAll();
    }
    
    @Override
    public Optional<Payroll> findById(Integer id) {
        return payrollRepository.findById(id);
    }
    
    @Override
    public List<Payroll> findByEmployeeId(Integer employeeId) {
        return payrollRepository.findByEmployeeEmployeeId(employeeId);
    }
    
    @Override
    public List<Payroll> findByMonth(String month) {
        return payrollRepository.findByMonth(month);
    }
    
    @Override
    public Payroll save(Payroll payroll) {
        return payrollRepository.save(payroll);
    }
    
    @Override
    public void deleteById(Integer id) {
        payrollRepository.deleteById(id);
    }
    
    /**
     * UC06 - Calculate payroll for given employee & month
     * Formula: (Days Worked * Daily Rate + Bonus - Fine - Tax - Insurance)
     */
    @Override
    public Payroll calculatePayroll(Integer employeeId, String month, Integer bonusAmount, Integer fineAmount) {
        // Fetch attendance records for the month (simplified - in production, query by month date range)
        List<Attendance> attendances = attendanceRepository.findByEmployeeEmployeeId(employeeId);
        
        // Count days worked (simple count; in production, validate status = "present" etc)
        int daysWorked = (int) attendances.stream()
                .filter(a -> a.getStatus() != null && a.getStatus().equalsIgnoreCase("present"))
                .count();
        
        // Ensure reasonable defaults
        if (daysWorked == 0) daysWorked = 20; // Default working days/month
        if (bonusAmount == null) bonusAmount = 0;
        if (fineAmount == null) fineAmount = 0;
        
        // Calculate salary components
        int baseSalary = daysWorked * DAILY_BASE_SALARY;
        int tax = (int) (baseSalary * TAX_RATE);
        int insurance = (int) (baseSalary * INSURANCE_RATE);
        int totalSalary = baseSalary + bonusAmount - fineAmount - tax - insurance;
        
        // Create or update Payroll record
        Payroll payroll = new Payroll(employeeId, month);
        payroll.setDaysWorked(daysWorked);
        payroll.setBonus(bonusAmount);
        payroll.setPenalty(fineAmount);
        payroll.setTax(tax);
        payroll.setInsurance(insurance);
        payroll.setTotalSalary(totalSalary);
        
        return save(payroll);
    }
}

