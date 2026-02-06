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

import io.github.ngthduongg623.enterprise_manager.entity.OvertimeRecord;
import io.github.ngthduongg623.enterprise_manager.service.EmployeeDetailService;
import io.github.ngthduongg623.enterprise_manager.service.OvertimeRecordService;

@Controller
@RequestMapping("/overtime")
public class OvertimeController {

    private final OvertimeRecordService overtimeRecordService;
    private final EmployeeDetailService employeeService;

    public OvertimeController(OvertimeRecordService overtimeRecordService, EmployeeDetailService employeeService) {
        this.overtimeRecordService = overtimeRecordService;
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listOvertime(@RequestParam(required = false) String month,
                               @RequestParam(required = false) String employeeName,
                               Model model) {
        List<OvertimeRecord> records = overtimeRecordService.findAll();

        if (month != null && !month.isEmpty()) {
            records = records.stream()
                    .filter(r -> r.getDate().toString().startsWith(month))
                    .collect(Collectors.toList());
        }

        if (employeeName != null && !employeeName.isEmpty()) {
            records = records.stream()
                    .filter(r -> r.getEmployee() != null && 
                                 r.getEmployee().getName().toLowerCase().contains(employeeName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("overtimeRecords", records);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedEmployeeName", employeeName);
        return "overtime/list";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        OvertimeRecord record = new OvertimeRecord();
        model.addAttribute("overtimeRecord", record);
        model.addAttribute("employees", employeeService.findAll());
        return "overtime/form";
    }

    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<OvertimeRecord> record = overtimeRecordService.findById(id);
        if (record.isPresent()) {
            model.addAttribute("overtimeRecord", record.get());
            model.addAttribute("employees", employeeService.findAll());
            return "overtime/form";
        }
        return "redirect:/overtime/list";
    }

    @PostMapping("/save")
    public String saveOvertime(@ModelAttribute("overtimeRecord") OvertimeRecord overtimeRecord) {
        overtimeRecordService.save(overtimeRecord);
        return "redirect:/overtime/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteOvertime(@PathVariable("id") Integer id) {
        overtimeRecordService.deleteById(id);
        return "redirect:/overtime/list";
    }
}