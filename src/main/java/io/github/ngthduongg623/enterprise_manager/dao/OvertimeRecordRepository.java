package io.github.ngthduongg623.enterprise_manager.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ngthduongg623.enterprise_manager.entity.OvertimeRecord;

public interface OvertimeRecordRepository extends JpaRepository<OvertimeRecord, Integer> {
    List<OvertimeRecord> findByEmployeeEmployeeId(Integer employeeId);
    List<OvertimeRecord> findByEmployeeEmployeeIdAndDate(Integer employeeId, LocalDate date);
    List<OvertimeRecord> findByDate(LocalDate date);
}
