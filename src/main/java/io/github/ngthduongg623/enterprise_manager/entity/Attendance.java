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
 * Chấm công (Attendance) - Ghi nhận thời gian vào/ra của nhân viên
 */
@Entity
@Table(name = "attendances")
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private EmployeeDetail employee;
    
    @Column(name = "date")
    private LocalDate date; // Ngày chấm công
    
    @Column(name = "start_time")
    private LocalDateTime startTime; // Giờ vào
    
    @Column(name = "end_time")
    private LocalDateTime endTime; // Giờ ra
    
    @Column(name = "status", length = 50)
    private String status; // Trạng thái: Present, Absent, Late, Left Early, etc.
    
    public Attendance() {
    }
    
    public Attendance(EmployeeDetail employee, LocalDate date) {
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
