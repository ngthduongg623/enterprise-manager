package io.github.ngthduongg623.enterprise_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.ngthduongg623.enterprise_manager.dao.OvertimeRecordRepository;
import io.github.ngthduongg623.enterprise_manager.entity.OvertimeRecord;

@Service
public class OvertimeRecordServiceImpl implements OvertimeRecordService {
    
    private final OvertimeRecordRepository overtimeRecordRepository;
    
    @Autowired
    public OvertimeRecordServiceImpl(OvertimeRecordRepository overtimeRecordRepository) {
        this.overtimeRecordRepository = overtimeRecordRepository;
    }
    
    @Override
    public List<OvertimeRecord> findAll() {
        return overtimeRecordRepository.findAll();
    }
    
    @Override
    public Optional<OvertimeRecord> findById(Integer id) {
        return overtimeRecordRepository.findById(id);
    }
    
    @Override
    public List<OvertimeRecord> findByEmployeeId(Integer employeeId) {
        return overtimeRecordRepository.findByEmployeeEmployeeId(employeeId);
    }
    
    @Override
    public List<OvertimeRecord> findByEmployeeIdAndDate(Integer employeeId, LocalDate date) {
        return overtimeRecordRepository.findByEmployeeEmployeeIdAndDate(employeeId, date);
    }
    
    @Override
    public List<OvertimeRecord> findByDate(LocalDate date) {
        return overtimeRecordRepository.findByDate(date);
    }
    
    @Override
    public OvertimeRecord save(OvertimeRecord overtimeRecord) {
        return overtimeRecordRepository.save(overtimeRecord);
    }
    
    @Override
    public void deleteById(Integer id) {
        overtimeRecordRepository.deleteById(id);
    }
}
