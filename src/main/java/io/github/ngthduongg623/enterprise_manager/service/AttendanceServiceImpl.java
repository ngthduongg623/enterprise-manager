package io.github.ngthduongg623.enterprise_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.ngthduongg623.enterprise_manager.dao.AttendanceRepository;
import io.github.ngthduongg623.enterprise_manager.entity.Attendance;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    
    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
    
    @Override
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }
    
    @Override
    public Optional<Attendance> findById(Integer id) {
        return attendanceRepository.findById(id);
    }
    
    @Override
    public List<Attendance> findByEmployeeId(Integer employeeId) {
        return attendanceRepository.findByEmployeeEmployeeId(employeeId);
    }
    
    @Override
    public List<Attendance> findByEmployeeIdAndDate(Integer employeeId, LocalDate date) {
        return attendanceRepository.findByEmployeeEmployeeIdAndDate(employeeId, date);
    }
    
    @Override
    public List<Attendance> findByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }
    
    @Override
    public Attendance save(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
    
    @Override
    public void deleteById(Integer id) {
        attendanceRepository.deleteById(id);
    }
}
