package io.github.ngthduongg623.enterprise_manager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ngthduongg623.enterprise_manager.entity.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Integer> {
    List<Payroll> findByEmployeeId(Integer employeeId);
    List<Payroll> findByMonth(String month);
    List<Payroll> findByEmployeeEmployeeId(Integer employeeId);
}
