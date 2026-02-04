package io.github.ngthduongg623.enterprise_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Phòng ban (Department)
 */
@Entity
@Table(name = "departments")
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "logo", length = 30)
    private String logo;
    
    @Column(name = "id_department")
    private Integer idDepartment; // Self-reference: Phòng ban cha (nếu có)
    
    @Column(name = "name", length = 30, nullable = false)
    @NotBlank(message = "Tên phòng ban không được để trống")
    private String name;
    
    public Department() {
    }
    
    public Department(String name) {
        this.name = name;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public Integer getIdDepartment() {
        return idDepartment;
    }
    
    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
