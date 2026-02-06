package io.github.ngthduongg623.enterprise_manager.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.ngthduongg623.enterprise_manager.entity.EmployeeDetail;
import io.github.ngthduongg623.enterprise_manager.service.DepartmentService;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeDetailService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeDetailService employeeService;
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeDetailService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/list")
    public String listEmployees(@RequestParam(required = false) String keyword, Model model) {
        List<EmployeeDetail> allEmployees = employeeService.findAll();
        List<EmployeeDetail> employees = allEmployees;

        if (keyword != null && !keyword.isEmpty()) {
            employees = allEmployees.stream()
                    .filter(e -> e.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("employees", employees);
        model.addAttribute("allEmployees", allEmployees);
        model.addAttribute("keyword", keyword);
        return "employees/list";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        EmployeeDetail employee = new EmployeeDetail();
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.findAll());
        return "employees/form";
    }

    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<EmployeeDetail> employee = employeeService.findByEmployeeId(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            model.addAttribute("departments", departmentService.findAll());
            return "employees/form";
        }
        return "redirect:/employees/list";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") EmployeeDetail employee) {
        employeeService.save(employee);
        return "redirect:/employees/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.deleteByEmployeeId(id);
        return "redirect:/employees/list";
    }
    
    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        String email = principal.getName();
        Optional<EmployeeDetail> employee = employeeService.findByEmail(email);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employees/profile";
        }
        return "redirect:/";
    }
}