package io.github.ngthduongg623.enterprise_manager.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Chi tiết nhân viên (EmployeeDetail) - Thông tin chi tiết của nhân viên
 * Tương ứng với bảng users trong cơ sở dữ liệu
 */
@Entity
@Table(name = "users")
public class EmployeeDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId; // Khóa chính
    
    @NotBlank(message = "Tên nhân viên không được để trống")
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department", referencedColumnName = "id", nullable = false)
    private Department department;
    
    @Column(name = "address", length = 100)
    private String address;
    
    @Column(name = "birth")
    private LocalDate birth; // Ngày sinh
    
    @Email
    @Column(name = "email", length = 100, nullable = false)
    @NotBlank(message = "Email không được để trống")
    private String email; // Khóa tham chiếu đến accounts
    
    @Column(name = "gender", length = 5)
    private String gender;
    
    @Column(name = "numberphone", length = 10)
    private String numberphone;
    
    @Column(name = "joinDate")
    private LocalDate joinDate; // Ngày vào công ty
    
    @Column(name = "role", length = 100)
    private String role;
    
    public EmployeeDetail() {
    }
    
    public EmployeeDetail(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters and Setters
    public Integer getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public LocalDate getBirth() {
        return birth;
    }
    
    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getNumberphone() {
        return numberphone;
    }
    
    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }
    
    public LocalDate getJoinDate() {
        return joinDate;
    }
    
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
