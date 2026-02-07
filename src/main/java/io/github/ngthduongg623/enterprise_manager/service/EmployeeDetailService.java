package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;

/**
 * Quản lý nhân viên, phòng ban:
 * Quản lý thông tin nhân viên;
 * Xem thông tin nhân viên;
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
