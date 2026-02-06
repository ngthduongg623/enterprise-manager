package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.ngthduongg623.enterprise_manager.dao.DepartmentRepository;
import io.github.ngthduongg623.enterprise_manager.entity.Department;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    
    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
    
    @Override
    public Optional<Department> findById(Integer id) {
        return departmentRepository.findById(id);
    }
    
    @Override
    public Department findByName(String name) {
        return departmentRepository.findByName(name);
    }
    
    @Override
    public boolean existsByName(String name) {
        return departmentRepository.findByName(name) != null;
    }
    
    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }
    
    @Override
    public void deleteById(Integer id) {
        departmentRepository.deleteById(id);
    }
}
