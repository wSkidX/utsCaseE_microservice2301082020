package com.uts.task_service.service;

import com.uts.task_service.model.Task;
import com.uts.task_service.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        try {
            Task saved = taskRepository.save(task);
            logger.info("[SERVICE][CREATE] Task created | id={}", saved.getId());
            return saved;
        } catch (Exception e) {
            logger.error("[SERVICE][CREATE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Task> getAllTask() {
        try {
            List<Task> list = taskRepository.findAll();
            logger.info("[SERVICE][READ_ALL] Task count={}", list.size());
            return list;
        } catch (Exception e) {
            logger.error("[SERVICE][READ_ALL] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<Task> getTaskById(Long id) {
        try {
            Optional<Task> task = taskRepository.findById(id);
            logger.info("[SERVICE][READ_BY_ID] Task id={} found={}", id, task.isPresent());
            return task;
        } catch (Exception e) {
            logger.error("[SERVICE][READ_BY_ID] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Optional<Task> updateTask(Long id, Task details) {
        try {
            Optional<Task> taskOptional = taskRepository.findById(id);
            if (taskOptional.isPresent()) {
                Task task = taskOptional.get();
                task.setTitle(details.getTitle());
                task.setDescription(details.getDescription());
                task.setStatus(details.getStatus());
                task.setEmployeeId(details.getEmployeeId());
                Task updated = taskRepository.save(task);
                logger.info("[SERVICE][UPDATE] Task updated | id={}", id);
                return Optional.of(updated);
            } else {
                logger.warn("[SERVICE][UPDATE] Task not found | id={}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("[SERVICE][UPDATE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public boolean deleteTask(Long id) {
        try {
            if (taskRepository.existsById(id)) {
                taskRepository.deleteById(id);
                logger.info("[SERVICE][DELETE] Task deleted | id={}", id);
                return true;
            } else {
                logger.warn("[SERVICE][DELETE] Task not found | id={}", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("[SERVICE][DELETE] Error: {}", e.getMessage(), e);
            throw e;
        }
    }
}
