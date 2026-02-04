package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.ngthduongg623.enterprise_manager.dao.EmployeeDetailRepository;
import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;

@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {
    
    private final EmployeeDetailRepository employeeDetailRepository;
    
    @Autowired
    public EmployeeDetailServiceImpl(EmployeeDetailRepository employeeDetailRepository) {
        this.employeeDetailRepository = employeeDetailRepository;
    }
    
    @Override
    public List<EmployeeDetail> findAll() {
        return employeeDetailRepository.findAll();
    }
    
    @Override
    public Optional<EmployeeDetail> findByEmployeeId(Integer employeeId) {
        return employeeDetailRepository.findByEmployeeId(employeeId);
    }
    
    @Override
    public Optional<EmployeeDetail> findByEmail(String email) {
        return employeeDetailRepository.findByEmail(email);
    }
    
    @Override
    public List<EmployeeDetail> findByDepartmentId(Integer departmentId) {
        return employeeDetailRepository.findByDepartmentId(departmentId);
    }
    
    @Override
    public EmployeeDetail save(EmployeeDetail employeeDetail) {
        return employeeDetailRepository.save(employeeDetail);
    }
    
    @Override
    public void deleteByEmployeeId(Integer employeeId) {
        employeeDetailRepository.deleteById(employeeId);
    }
    
    @Override
    public boolean existsByEmployeeId(Integer employeeId) {
        return employeeDetailRepository.existsById(employeeId);
    }
}
