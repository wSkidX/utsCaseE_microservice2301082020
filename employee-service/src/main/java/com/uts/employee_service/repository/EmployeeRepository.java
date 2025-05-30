package com.uts.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uts.employee_service.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
