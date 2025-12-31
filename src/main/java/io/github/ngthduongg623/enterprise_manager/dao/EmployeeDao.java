package io.github.ngthduongg623.enterprise_manager.dao;

import io.github.ngthduongg623.enterprise_manager.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> findAll();
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteByID(int id);


}
