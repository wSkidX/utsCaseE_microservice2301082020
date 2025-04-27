package com.uts.task_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uts.task_service.model.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {

}
