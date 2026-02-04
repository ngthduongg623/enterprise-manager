package io.github.ngthduongg623.enterprise_manager.controller;

import java.time.LocalDate;
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

import io.github.ngthduongg623.enterprise_manager.entity.Attendance;
import io.github.ngthduongg623.enterprise_manager.service.AttendanceService;

/**
 * UC05 - Quản lý chấm công (Attendance Management)
 * UC07 - Xem dữ liệu chấm công (View Attendance Data)
 */
@RestController
@RequestMapping("/api/attendances")
public class AttendanceRestController {
    
    private final AttendanceService attendanceService;
    
    @Autowired
    public AttendanceRestController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    
    // CRUD endpoints for Admin
    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        Attendance created = attendanceService.save(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Integer id, @RequestBody Attendance attendance) {
        Optional<Attendance> existing = attendanceService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        attendance.setId(id);
        Attendance updated = attendanceService.save(attendance);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Integer id) {
        Optional<Attendance> attendance = attendanceService.findById(id);
        if (attendance.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        attendanceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Read-only endpoints for Employee & Admin
    @GetMapping
    public ResponseEntity<List<Attendance>> findAll() {
        List<Attendance> attendances = attendanceService.findAll();
        return ResponseEntity.ok(attendances);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> findById(@PathVariable Integer id) {
        Optional<Attendance> attendance = attendanceService.findById(id);
        return attendance.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<Attendance>> findByEmployee(@PathVariable Integer employeeId) {
        List<Attendance> attendances = attendanceService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(attendances);
    }
    
    @GetMapping("/by-employee-and-date/{employeeId}/{date}")
    public ResponseEntity<List<Attendance>> findByEmployeeAndDate(@PathVariable Integer employeeId, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Attendance> attendances = attendanceService.findByEmployeeIdAndDate(employeeId, localDate);
        return ResponseEntity.ok(attendances);
    }
    
    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<Attendance>> findByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Attendance> attendances = attendanceService.findByDate(localDate);
        return ResponseEntity.ok(attendances);
    }
}
