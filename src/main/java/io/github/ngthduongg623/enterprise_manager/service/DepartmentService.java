package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.Department;

/**
 * UC03 - Quản lý phòng ban (Department Management)
 * UC04 - Xem thông tin phòng ban (View Department Info)
 */
public interface DepartmentService {
    List<Department> findAll();
    
    Optional<Department> findById(Integer id);
    
    Department findByName(String name);

    List<Department> findByIdDepartment(Integer idDepartment);
    
    Department save(Department department);
    
    void deleteById(Integer id);
}
