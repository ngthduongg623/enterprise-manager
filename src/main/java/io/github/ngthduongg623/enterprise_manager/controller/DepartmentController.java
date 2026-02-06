package io.github.ngthduongg623.enterprise_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.ngthduongg623.enterprise_manager.entity.Department;
import io.github.ngthduongg623.enterprise_manager.service.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/list")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "departments/list";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        Department department = new Department();
        model.addAttribute("department", department);
        return "departments/form";
    }

    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute("department") Department department) {
        departmentService.save(department);
        return "redirect:/departments/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Integer id) {
        departmentService.deleteById(id);
        return "redirect:/departments/list";
    }
}