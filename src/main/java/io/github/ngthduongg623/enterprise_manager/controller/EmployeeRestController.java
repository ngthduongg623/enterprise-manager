package io.github.ngthduongg623.enterprise_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<EmployeeDetail> findAll(){

        return employeeService.findAll();
    }
    @GetMapping("/employees/{employeeId}")
    public EmployeeDetail findById(@PathVariable int employeeId) {
        EmployeeDetail employee =employeeService.findById(employeeId);
        if (employee ==null){
            throw new EmployeeNotFoundException("employee id not found - "+employeeId);
        }
        return employee;
    }
    @PostMapping("/employees")
    public EmployeeDetail addEmployee(@RequestBody EmployeeDetail employee){
        //in case of passing an id via json we gonna set it to 0 to do insert instead of an update
        employee.setEmployeeId(0);

        return employeeService.save(employee); // it has updated id from DB in case of insert
    }
    @PutMapping("/employees")
    public EmployeeDetail updateEmployee(@RequestBody EmployeeDetail employee){
        EmployeeDetail dbEmployee =employeeService.save(employee);
        return employee;
    }
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        EmployeeDetail tempEmployee =employeeService.findById(employeeId);
        if (tempEmployee == null)throw  new EmployeeNotFoundException("employee id not found - "+employeeId);
        employeeService.deleteById(employeeId);
        return "deleted employee id - "+employeeId ;
    }
}
