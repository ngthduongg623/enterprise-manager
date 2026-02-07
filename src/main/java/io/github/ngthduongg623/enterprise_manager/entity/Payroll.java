package io.github.ngthduongg623.enterprise_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Bảng lương (Payroll) - Ghi nhận chi tiết lương của nhân viên theo tháng
 */
@Entity
@Table(name = "payrolls")
public class Payroll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private EmployeeDetail employee;
    
    @Column(name = "employee_id", insertable = false, updatable = false)
    private Integer employeeId;
    
    @Column(name = "thang", length = 15)
    private String month;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department", referencedColumnName = "id")
    private Department department;
    
    @Column(name = "ngay_cong")
    private Integer daysWorked; // Số ngày công
    
    @Column(name = "thuong")
    private Integer bonus; // Tiền thưởng
    
    @Column(name = "phat")
    private Integer penalty; // Tiền phạt
    
    @Column(name = "thue")
    private Integer tax; // Mức thuế TNCN
    
    @Column(name = "bao_hiem")
    private Integer insurance; // Mức đóng BHXH
    
    @Column(name = "tong_cong")
    private Integer totalSalary; // Tổng lương thực lĩnh
    
    public Payroll() {
    }
    
    public Payroll(Integer employeeId, String month) {
        this.employeeId = employeeId;
        this.month = month;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getMonth() {
        return month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }
    
    public EmployeeDetail getEmployee() {
        return employee;
    }
    
    public void setEmployee(EmployeeDetail employee) {
        this.employee = employee;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public Integer getDaysWorked() {
        return daysWorked;
    }
    
    public void setDaysWorked(Integer daysWorked) {
        this.daysWorked = daysWorked;
    }
    
    public Integer getBonus() {
        return bonus;
    }
    
    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
    
    public Integer getPenalty() {
        return penalty;
    }
    
    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }
    
    public Integer getTax() {
        return tax;
    }
    
    public void setTax(Integer tax) {
        this.tax = tax;
    }
    
    public Integer getInsurance() {
        return insurance;
    }
    
    public void setInsurance(Integer insurance) {
        this.insurance = insurance;
    }
    
    public Integer getTotalSalary() {
        return totalSalary;
    }
    
    public void setTotalSalary(Integer totalSalary) {
        this.totalSalary = totalSalary;
    }
}
