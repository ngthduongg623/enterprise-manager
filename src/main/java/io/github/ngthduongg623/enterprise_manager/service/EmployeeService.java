package io.github.ngthduongg623.enterprise_manager.service;

import io.github.ngthduongg623.enterprise_manager.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    void deleteById(int theId);

}