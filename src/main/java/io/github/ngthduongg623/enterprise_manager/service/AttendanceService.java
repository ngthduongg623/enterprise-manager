package io.github.ngthduongg623.enterprise_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.github.ngthduongg623.enterprise_manager.entity.Attendance;

/**
 * UC05 - Quản lý chấm công (Attendance Management)
 * UC07 - Xem dữ liệu chấm công (View Attendance Data)
 */
public interface AttendanceService {
    List<Attendance> findAll();
    
    Optional<Attendance> findById(Integer id);
    
    List<Attendance> findByEmployeeId(Integer employeeId);
    
    List<Attendance> findByEmployeeIdAndDate(Integer employeeId, LocalDate date);
    
    List<Attendance> findByDate(LocalDate date);
    
    Attendance save(Attendance attendance);
    
    void deleteById(Integer id);
}
