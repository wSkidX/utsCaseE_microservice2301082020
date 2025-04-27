package com.uts.employee_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uts.employee_service.model.Employee;
import com.uts.employee_service.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    // CREATE
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        try {
            Employee savedEmployee = employeeService.createEmployee(employee);
            logger.info("[CONTROLLER][CREATE] Employee created | id={}", savedEmployee.getId());
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("[CONTROLLER][CREATE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee(){
        try {
            List<Employee> employees = employeeService.getAllEmployee();
            logger.info("[CONTROLLER][READ_ALL] Employee count={}", employees.size());
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[CONTROLLER][READ_ALL] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        try {
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            if (employee.isPresent()) {
                logger.info("[CONTROLLER][READ_BY_ID] Employee found | id={}", id);
                return new ResponseEntity<>(employee.get(), HttpStatus.OK);
            } else {
                logger.warn("[CONTROLLER][READ_BY_ID] Employee not found | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][READ_BY_ID] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        try {
            Optional<Employee> updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            if(updatedEmployee.isPresent()){
                logger.info("[CONTROLLER][UPDATE] Employee updated | id={}", id);
                return new ResponseEntity<>(updatedEmployee.get(), HttpStatus.OK);
            } else {
                logger.warn("[CONTROLLER][UPDATE] Employee not found for update | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][UPDATE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        try {
            boolean deleted = employeeService.deleteEmployee(id);
            if(deleted){
                logger.info("[CONTROLLER][DELETE] Employee deleted | id={}", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.warn("[CONTROLLER][DELETE] Employee not found for delete | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][DELETE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
