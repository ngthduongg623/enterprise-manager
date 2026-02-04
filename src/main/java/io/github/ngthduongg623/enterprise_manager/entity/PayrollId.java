package io.github.ngthduongg623.enterprise_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Composite key for Payroll entity (EmployeeId + Month)
 */
@Embeddable
public class PayrollId implements Serializable {
    
    @Column(name = "employee_id")
    private Integer employeeId;
    
    @Column(name = "thang")
    private String month;
    
    public PayrollId() {
    }
    
    public PayrollId(Integer employeeId, String month) {
        this.employeeId = employeeId;
        this.month = month;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayrollId payrollId = (PayrollId) o;
        return Objects.equals(employeeId, payrollId.employeeId) && Objects.equals(month, payrollId.month);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(employeeId, month);
    }
}
