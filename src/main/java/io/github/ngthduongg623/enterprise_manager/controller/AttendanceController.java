package io.github.ngthduongg623.enterprise_manager.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.ngthduongg623.enterprise_manager.entity.Attendance;
import io.github.ngthduongg623.enterprise_manager.service.AttendanceService;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeDetailService;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EmployeeDetailService employeeService;

    public AttendanceController(AttendanceService attendanceService, EmployeeDetailService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listAttendance(@RequestParam(required = false) String month,
                                 @RequestParam(required = false) String employeeName,
                                 Model model) {
        List<Attendance> attendances = attendanceService.findAll();

        if (month != null && !month.isEmpty()) {
            attendances = attendances.stream()
                    .filter(a -> a.getDate().toString().startsWith(month))
                    .collect(Collectors.toList());
        }

        if (employeeName != null && !employeeName.isEmpty()) {
            attendances = attendances.stream()
                    .filter(a -> a.getEmployee() != null && 
                                 a.getEmployee().getName().toLowerCase().contains(employeeName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("attendances", attendances);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedEmployeeName", employeeName);
        return "attendance/list";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        Attendance attendance = new Attendance();
        model.addAttribute("attendance", attendance);
        model.addAttribute("employees", employeeService.findAll());
        return "attendance/form";
    }

    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<Attendance> attendance = attendanceService.findById(id);
        if (attendance.isPresent()) {
            model.addAttribute("attendance", attendance.get());
            model.addAttribute("employees", employeeService.findAll());
            return "attendance/form";
        }
        return "redirect:/attendance/list";
    }

    @PostMapping("/save")
    public String saveAttendance(@ModelAttribute("attendance") Attendance attendance) {
        attendanceService.save(attendance);
        return "redirect:/attendance/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAttendance(@PathVariable("id") Integer id) {
        attendanceService.deleteById(id);
        return "redirect:/attendance/list";
    }
}