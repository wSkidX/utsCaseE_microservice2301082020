package com.uts.attendance_service.service;

import com.uts.attendance_service.model.Attendance;
import com.uts.attendance_service.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttedanceService {
    private static final Logger logger = LoggerFactory.getLogger(AttedanceService.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance createAttendance(Attendance attendance) {
        try {
            Attendance saved = attendanceRepository.save(attendance);
            logger.info("[SERVICE][CREATE] Attendance created | id={}", saved.getId());
            return saved;
        } catch (Exception e) {
            logger.error("[SERVICE][CREATE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Attendance> getAllAttendance() {
        try {
            List<Attendance> list = attendanceRepository.findAll();
            logger.info("[SERVICE][READ_ALL] Attendance count={}", list.size());
            return list;
        } catch (Exception e) {
            logger.error("[SERVICE][READ_ALL] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<Attendance> getAttendanceById(Long id) {
        try {
            Optional<Attendance> attendance = attendanceRepository.findById(id);
            logger.info("[SERVICE][READ_BY_ID] Attendance id={} found={}", id, attendance.isPresent());
            return attendance;
        } catch (Exception e) {
            logger.error("[SERVICE][READ_BY_ID] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<Attendance> updateAttendance(Long id, Attendance details) {
        try {
            Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
            if (attendanceOptional.isPresent()) {
                Attendance attendance = attendanceOptional.get();
                attendance.setEmployeeId(details.getEmployeeId());
                attendance.setDate(details.getDate());
                attendance.setStatus(details.getStatus());
                Attendance updated = attendanceRepository.save(attendance);
                logger.info("[SERVICE][UPDATE] Attendance updated | id={}", id);
                return Optional.of(updated);
            } else {
                logger.warn("[SERVICE][UPDATE] Attendance not found | id={}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("[SERVICE][UPDATE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public boolean deleteAttendance(Long id) {
        try {
            if (attendanceRepository.existsById(id)) {
                attendanceRepository.deleteById(id);
                logger.info("[SERVICE][DELETE] Attendance deleted | id={}", id);
                return true;
            } else {
                logger.warn("[SERVICE][DELETE] Attendance not found | id={}", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("[SERVICE][DELETE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }
}
