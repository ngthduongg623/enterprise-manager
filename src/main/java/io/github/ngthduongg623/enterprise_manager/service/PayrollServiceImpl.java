package io.github.ngthduongg623.enterprise_manager.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.ngthduongg623.enterprise_manager.dao.AttendanceRepository;
import io.github.ngthduongg623.enterprise_manager.dao.EmployeeDetailRepository;
import io.github.ngthduongg623.enterprise_manager.dao.OvertimeRecordRepository;
import io.github.ngthduongg623.enterprise_manager.dao.PayrollRepository;
import io.github.ngthduongg623.enterprise_manager.entity.Attendance;
import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;
import io.github.ngthduongg623.enterprise_manager.entity.OvertimeRecord;
import io.github.ngthduongg623.enterprise_manager.entity.Payroll;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeDetailRepository employeeDetailRepository;
    private final AttendanceRepository attendanceRepository;
    private final OvertimeRecordRepository overtimeRecordRepository;

    public PayrollServiceImpl(PayrollRepository payrollRepository, EmployeeDetailRepository employeeDetailRepository, AttendanceRepository attendanceRepository, OvertimeRecordRepository overtimeRecordRepository) {
        this.payrollRepository = payrollRepository;
        this.employeeDetailRepository = employeeDetailRepository;
        this.attendanceRepository = attendanceRepository;
        this.overtimeRecordRepository = overtimeRecordRepository;
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
        return payrollRepository.findAll().stream()
                .filter(p -> month.equals(p.getMonth()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Payroll> findByEmployeeIdAndMonth(Integer employeeId, String month) {
        return findByEmployeeId(employeeId).stream()
                .filter(p -> month.equals(p.getMonth()))
                .findFirst();
    }

    @Override
    @Transactional
    public Payroll save(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        payrollRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void calculateMonthlyPayroll(String month, Integer finePerLate, Integer deduction, Double insuranceRate) {
        List<EmployeeDetail> employees = employeeDetailRepository.findAll();
        for (EmployeeDetail emp : employees) {
            calculatePayroll(emp.getEmployeeId(), month, 0, finePerLate, deduction, insuranceRate);
        }
    }

    @Override
    public Payroll calculatePayroll(Integer employeeId, String month) {
        return calculatePayroll(employeeId, month, 0, 0, 0, 10.5);
    }

    @Override
    @Transactional
    public Payroll calculatePayroll(Integer employeeId, String month, Integer bonusAmount, Integer finePerLate, Integer deduction, Double insuranceRate) {
        EmployeeDetail employee = employeeDetailRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 1. Xác định Công Chuẩn (Standard Work Days)
        YearMonth yearMonth = YearMonth.parse(month);
        int daysInMonth = yearMonth.lengthOfMonth();
        int standardWorkDays = 0;
        for (int i = 1; i <= daysInMonth; i++) {
            DayOfWeek dow = yearMonth.atDay(i).getDayOfWeek();
            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
                standardWorkDays++;
            }
        }

        // 2. Xác định Công Thực (Actual Work Days) & Số lần muộn
        List<Attendance> attendances = attendanceRepository.findByEmployeeEmployeeId(employeeId).stream()
                .filter(a -> a.getDate().toString().startsWith(month))
                .collect(Collectors.toList());
        
        long actualWorkDays = attendances.stream()
                .filter(a -> "Present".equals(a.getStatus()) || "Late".equals(a.getStatus()))
                .count();
        
        long lateCount = attendances.stream()
                .filter(a -> "Late".equals(a.getStatus()))
                .count();

        // 3. Tính lương cơ bản theo công
        int baseSalary = employee.getBaseSalary() != null ? employee.getBaseSalary() : 0;
        double dailyRate = standardWorkDays > 0 ? (double) baseSalary / standardWorkDays : 0;
        double salaryByWork = dailyRate * actualWorkDays;

        // 4. Tính Tăng Ca (Overtime)
        List<OvertimeRecord> overtimeRecords = overtimeRecordRepository.findByEmployeeEmployeeId(employeeId).stream()
                .filter(o -> o.getDate().toString().startsWith(month))
                .collect(Collectors.toList());
        
        double hourlyRate = dailyRate / 8.0;
        double overtimePay = 0;
        for (OvertimeRecord record : overtimeRecords) {
            DayOfWeek dow = record.getDate().getDayOfWeek();
            double multiplier = (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) ? 2.0 : 1.5;
            // Note: Holiday logic (300%) requires a Holiday calendar, omitted for simplicity as per prompt context
            double hours = 0;
            if (record.getStartTime() != null && record.getEndTime() != null) {
                hours = Duration.between(record.getStartTime(), record.getEndTime()).toMinutes() / 60.0;
            }
            overtimePay += hours * hourlyRate * multiplier;
        }

        // 5. Tổng hợp
        int penalty = (int) (lateCount * (finePerLate != null ? finePerLate : 0));
        
        // Thu nhập làm căn cứ tính bảo hiểm và thuế (chưa trừ)
        double incomeBase = salaryByWork + overtimePay - penalty;
        if (incomeBase < 0) incomeBase = 0;

        // Bảo hiểm = ((Lương_CB / Công_Chuẩn) * Công_Thực + Tăng_Ca - Phạt_Muộn) * Tỷ_Lệ
        int insurance = (int) (incomeBase * (insuranceRate != null ? insuranceRate / 100.0 : 0));

        // Thu nhập tính thuế = Thu nhập căn cứ - Bảo hiểm - Giảm trừ
        double taxableIncome = incomeBase - insurance - (deduction != null ? deduction : 0);
        int tax = calculatePersonalIncomeTax(taxableIncome);

        int totalSalary = (int) (salaryByWork + overtimePay + (bonusAmount != null ? bonusAmount : 0) - penalty - insurance - tax);

        // Lưu vào DB
        Payroll payroll = findByEmployeeIdAndMonth(employeeId, month).orElse(new Payroll(employeeId, month));
        payroll.setEmployee(employee);
        payroll.setDepartment(employee.getDepartment());
        payroll.setDaysWorked((int) actualWorkDays);
        payroll.setBonus(bonusAmount);
        payroll.setPenalty(penalty);
        payroll.setInsurance(insurance);
        payroll.setTax(tax);
        payroll.setTotalSalary(totalSalary);

        return payrollRepository.save(payroll);
    }

    private int calculatePersonalIncomeTax(double taxableIncome) {
        if (taxableIncome <= 0) return 0;
        
        double incomeMillion = taxableIncome / 1_000_000.0;
        double taxMillion = 0;

        if (incomeMillion <= 10) { // Bậc 1: Đến 10 triệu
            taxMillion = incomeMillion * 0.05;
        } else if (incomeMillion <= 30) { // Bậc 2: Trên 10 đến 30
            taxMillion = 10 * 0.05 + (incomeMillion - 10) * 0.10;
        } else if (incomeMillion <= 60) { // Bậc 3: Trên 30 đến 60
            taxMillion = 10 * 0.05 + 20 * 0.10 + (incomeMillion - 30) * 0.20;
        } else if (incomeMillion <= 100) { // Bậc 4: Trên 60 đến 100
            taxMillion = 10 * 0.05 + 20 * 0.10 + 30 * 0.20 + (incomeMillion - 60) * 0.30;
        } else { // Bậc 5: Trên 100
            taxMillion = 10 * 0.05 + 20 * 0.10 + 30 * 0.20 + 40 * 0.30 + (incomeMillion - 100) * 0.35;
        }

        return (int) (taxMillion * 1_000_000);
    }

    @Override
    public long getTotalPayrollByMonth(String month) {
        return findByMonth(month).stream().mapToLong(Payroll::getTotalSalary).sum();
    }
}