package io.github.ngthduongg623.enterprise_manager.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Integer> {
    Optional<EmployeeDetail> findByEmail(String email);
    List<EmployeeDetail> findByDepartmentId(Integer departmentId);
    Optional<EmployeeDetail> findByEmployeeId(Integer employeeId);
}
