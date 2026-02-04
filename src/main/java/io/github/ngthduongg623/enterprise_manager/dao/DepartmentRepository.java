package io.github.ngthduongg623.enterprise_manager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ngthduongg623.enterprise_manager.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByName(String name);
    List<Department> findByIdDepartment(Integer idDepartment);
}
