package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.Department;

/**
 * Quản lý nhân viên, phòng ban
 * UC03 - Quản lý thông tin phòng ban
 * UC04 - Xem thông tin phòng ban
 */
public interface DepartmentService {
    List<Department> findAll();
    
    Optional<Department> findById(Integer id);
    
    Department findByName(String name);
    
    boolean existsByName(String name);

    
    Department save(Department department);
    
    void deleteById(Integer id);
}
