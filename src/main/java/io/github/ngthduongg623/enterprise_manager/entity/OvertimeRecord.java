package io.github.ngthduongg623.enterprise_manager.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * Tăng ca (OvertimeRecord) - Ghi nhận thời gian làm tăng ca của nhân viên
 */
@Entity
@Table(name = "overtime_records")
public class OvertimeRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private EmployeeDetail employee;
    
    @Column(name = "type", length = 50)
    private String type; // Loại tăng ca: Weekday, Weekend, Holiday
    
    @Column(name = "date")
    private LocalDate date; // Ngày tăng ca
    
    @Column(name = "start_time")
    private LocalDateTime startTime; // Giờ bắt đầu
    
    @Column(name = "end_time")
    private LocalDateTime endTime; // Giờ kết thúc
    
    public OvertimeRecord() {
    }
    
    public OvertimeRecord(EmployeeDetail employee, LocalDate date) {
        this.employee = employee;
        this.date = date;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public EmployeeDetail getEmployee() {
        return employee;
    }
    
    public void setEmployee(EmployeeDetail employee) {
        this.employee = employee;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
