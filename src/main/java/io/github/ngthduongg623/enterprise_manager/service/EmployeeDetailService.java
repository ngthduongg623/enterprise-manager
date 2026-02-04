package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;

/**
 * Dịch vụ quản lý chi tiết nhân viên (EmployeeDetail Service)
 */
public interface EmployeeDetailService {
    List<EmployeeDetail> findAll();
    
    Optional<EmployeeDetail> findByEmployeeId(Integer employeeId);
    
    Optional<EmployeeDetail> findByEmail(String email);
    
    List<EmployeeDetail> findByDepartmentId(Integer departmentId);
    
    EmployeeDetail save(EmployeeDetail employeeDetail);
    
    void deleteByEmployeeId(Integer employeeId);
    
    boolean existsByEmployeeId(Integer employeeId);
}
