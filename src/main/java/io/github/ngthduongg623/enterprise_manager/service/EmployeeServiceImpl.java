package io.github.ngthduongg623.enterprise_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.ngthduongg623.enterprise_manager.dao.EmployeeDetailRepository;
import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDetailRepository employeeDetailRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeDetailRepository theEmployeeDetailRepository) {
        employeeDetailRepository = theEmployeeDetailRepository;
    }

    @Override
    public List<EmployeeDetail> findAll() {
        return employeeDetailRepository.findAll();
    }

    @Override
    public EmployeeDetail findById(int theId) {
        Optional<EmployeeDetail> result = employeeDetailRepository.findById(theId);

        EmployeeDetail theEmployee = null;

        if (result.isPresent()) {
            theEmployee = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }

        return theEmployee;
    }

    @Override
    public Optional<EmployeeDetail> findByIdOptional(int theId) {
        return employeeDetailRepository.findById(theId);
    }

    @Override
    public EmployeeDetail save(EmployeeDetail theEmployee) {
        return employeeDetailRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        employeeDetailRepository.deleteById(theId);
    }
}
