package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;

public interface EmployeeService {

    List<EmployeeDetail> findAll();

    EmployeeDetail findById(int theId);

    Optional<EmployeeDetail> findByIdOptional(int theId);

    EmployeeDetail save(EmployeeDetail theEmployee);

    void deleteById(int theId);

}