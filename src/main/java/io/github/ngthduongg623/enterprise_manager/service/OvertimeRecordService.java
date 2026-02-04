package io.github.ngthduongg623.enterprise_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.OvertimeRecord;

/**
 * UC05 - Quản lý giờ làm thêm (Overtime Management)
 * UC07 - Xem dữ liệu giờ làm thêm (View Overtime Data)
 */
public interface OvertimeRecordService {
    List<OvertimeRecord> findAll();
    
    Optional<OvertimeRecord> findById(Integer id);
    
    List<OvertimeRecord> findByEmployeeId(Integer employeeId);
    
    List<OvertimeRecord> findByEmployeeIdAndDate(Integer employeeId, LocalDate date);
    
    List<OvertimeRecord> findByDate(LocalDate date);
    
    OvertimeRecord save(OvertimeRecord overtimeRecord);
    
    void deleteById(Integer id);
}
