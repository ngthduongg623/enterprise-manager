package io.github.ngthduongg623.enterprise_manager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ngthduongg623.enterprise_manager.entity.Department;
import io.github.ngthduongg623.enterprise_manager.service.DepartmentService;

/**
 * UC03 - Quản lý phòng ban (Department Management)
 * UC04 - Xem thông tin phòng ban (View Department Info)
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentRestController {
    
    private final DepartmentService departmentService;
    
    @Autowired
    public DepartmentRestController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
    // CRUD endpoints for Admin
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department created = departmentService.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        Optional<Department> existing = departmentService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        department.setId(id);
        Department updated = departmentService.save(department);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
        Optional<Department> department = departmentService.findById(id);
        if (department.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        departmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Read-only endpoints for Employee & Admin
    @GetMapping
    public ResponseEntity<List<Department>> findAll() {
        List<Department> departments = departmentService.findAll();
        return ResponseEntity.ok(departments);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Department> findById(@PathVariable Integer id) {
        Optional<Department> department = departmentService.findById(id);
        return department.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/by-name/{name}")
    public ResponseEntity<Department> findByName(@PathVariable String name) {
        Department department = departmentService.findByName(name);
        if (department == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(department);
    }
    
    @GetMapping("/by-manager/{manageId}")
    public ResponseEntity<List<Department>> findByManager(@PathVariable Integer manageId) {
        List<Department> departments = departmentService.findByIdDepartment(manageId);
        return ResponseEntity.ok(departments);
    }
}
