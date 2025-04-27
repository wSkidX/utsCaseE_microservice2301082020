package com.uts.attendance_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uts.attendance_service.model.Attendance;


public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
