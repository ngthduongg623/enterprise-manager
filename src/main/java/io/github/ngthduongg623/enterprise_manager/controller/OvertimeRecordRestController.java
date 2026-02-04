package io.github.ngthduongg623.enterprise_manager.controller;

import io.github.ngthduongg623.enterprise_manager.entity.OvertimeRecord;
import io.github.ngthduongg623.enterprise_manager.service.OvertimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * UC05 - Quản lý giờ làm thêm (Overtime Management)
 * UC07 - Xem dữ liệu giờ làm thêm (View Overtime Data)
 */
@RestController
@RequestMapping("/api/overtime-records")
public class OvertimeRecordRestController {
    
    private final OvertimeRecordService overtimeRecordService;
    
    @Autowired
    public OvertimeRecordRestController(OvertimeRecordService overtimeRecordService) {
        this.overtimeRecordService = overtimeRecordService;
    }
    
    @GetMapping
    public ResponseEntity<List<OvertimeRecord>> findAll() {
        List<OvertimeRecord> records = overtimeRecordService.findAll();
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OvertimeRecord> findById(@PathVariable Integer id) {
        Optional<OvertimeRecord> record = overtimeRecordService.findById(id);
        return record.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<OvertimeRecord>> findByEmployee(@PathVariable Integer employeeId) {
        List<OvertimeRecord> records = overtimeRecordService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<OvertimeRecord>> findByDate(@PathVariable LocalDate date) {
        List<OvertimeRecord> records = overtimeRecordService.findByDate(date);
        return ResponseEntity.ok(records);
    }
    
    @PostMapping
    public ResponseEntity<OvertimeRecord> createRecord(@RequestBody OvertimeRecord record) {
        OvertimeRecord created = overtimeRecordService.save(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<OvertimeRecord> updateRecord(@PathVariable Integer id, @RequestBody OvertimeRecord record) {
        Optional<OvertimeRecord> existing = overtimeRecordService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        record.setId(id);
        OvertimeRecord updated = overtimeRecordService.save(record);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Integer id) {
        Optional<OvertimeRecord> record = overtimeRecordService.findById(id);
        if (record.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        overtimeRecordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
