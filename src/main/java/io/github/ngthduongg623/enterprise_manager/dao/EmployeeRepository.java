package io.github.ngthduongg623.enterprise_manager.dao;

import io.github.ngthduongg623.enterprise_manager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}