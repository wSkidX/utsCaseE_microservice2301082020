package com.uts.attendance_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uts.attendance_service.model.Attendance;
import com.uts.attendance_service.service.AttedanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @Autowired
    private AttedanceService attedanceService;

    // CREATE
    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance){
        try {
            Attendance savedAttendance = attedanceService.createAttendance(attendance);
            logger.info("[CONTROLLER][CREATE] Attendance created | id={}", savedAttendance.getId());
            return new ResponseEntity<>(savedAttendance, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("[CONTROLLER][CREATE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance(){
        try {
            List<Attendance> attendances = attedanceService.getAllAttendance();
            logger.info("[CONTROLLER][READ_ALL] Attendance count={}", attendances.size());
            return new ResponseEntity<>(attendances, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[CONTROLLER][READ_ALL] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id){
        try {
            Optional<Attendance> attendance = attedanceService.getAttendanceById(id);
            if (attendance.isPresent()) {
                logger.info("[CONTROLLER][READ_BY_ID] Attendance found | id={}", id);
                return new ResponseEntity<>(attendance.get(), HttpStatus.OK);
            } else {
                logger.warn("[CONTROLLER][READ_BY_ID] Attendance not found | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][READ_BY_ID] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendanceDetails){
        try {
            Optional<Attendance> updatedAttendance = attedanceService.updateAttendance(id, attendanceDetails);
            if(updatedAttendance.isPresent()){
                logger.info("[CONTROLLER][UPDATE] Attendance updated | id={}", id);
                return new ResponseEntity<>(updatedAttendance.get(), HttpStatus.OK);
            } else {
                logger.warn("[CONTROLLER][UPDATE] Attendance not found for update | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][UPDATE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id){
        try {
            boolean deleted = attedanceService.deleteAttendance(id);
            if(deleted){
                logger.info("[CONTROLLER][DELETE] Attendance deleted | id={}", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.warn("[CONTROLLER][DELETE] Attendance not found for delete | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][DELETE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
