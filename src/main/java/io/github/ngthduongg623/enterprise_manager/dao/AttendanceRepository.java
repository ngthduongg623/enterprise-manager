package io.github.ngthduongg623.enterprise_manager.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ngthduongg623.enterprise_manager.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployeeEmployeeId(Integer employeeId);
    List<Attendance> findByEmployeeEmployeeIdAndDate(Integer employeeId, LocalDate date);
    List<Attendance> findByDate(LocalDate date);
}
