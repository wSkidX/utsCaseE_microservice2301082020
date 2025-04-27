package com.uts.employee_service.service;

import com.uts.employee_service.model.Employee;
import com.uts.employee_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        try {
            Employee saved = employeeRepository.save(employee);
            logger.info("[SERVICE][CREATE] Employee created | id={}", saved.getId());
            return saved;
        } catch (Exception e) {
            logger.error("[SERVICE][CREATE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Employee> getAllEmployee() {
        try {
            List<Employee> list = employeeRepository.findAll();
            logger.info("[SERVICE][READ_ALL] Employee count={}", list.size());
            return list;
        } catch (Exception e) {
            logger.error("[SERVICE][READ_ALL] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<Employee> getEmployeeById(Long id) {
        try {
            Optional<Employee> employee = employeeRepository.findById(id);
            logger.info("[SERVICE][READ_BY_ID] Employee id={} found={}", id, employee.isPresent());
            return employee;
        } catch (Exception e) {
            logger.error("[SERVICE][READ_BY_ID] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<Employee> updateEmployee(Long id, Employee details) {
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(id);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                employee.setName(details.getName());
                employee.setEmployeeId(details.getEmployeeId());
                employee.setEmail(details.getEmail());
                employee.setPosition(details.getPosition());
                Employee updated = employeeRepository.save(employee);
                logger.info("[SERVICE][UPDATE] Employee updated | id={}", id);
                return Optional.of(updated);
            } else {
                logger.warn("[SERVICE][UPDATE] Employee not found | id={}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("[SERVICE][UPDATE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public boolean deleteEmployee(Long id) {
        try {
            if (employeeRepository.existsById(id)) {
                employeeRepository.deleteById(id);
                logger.info("[SERVICE][DELETE] Employee deleted | id={}", id);
                return true;
            } else {
                logger.warn("[SERVICE][DELETE] Employee not found | id={}", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("[SERVICE][DELETE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }
}
