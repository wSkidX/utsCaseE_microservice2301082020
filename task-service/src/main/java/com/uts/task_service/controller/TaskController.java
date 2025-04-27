package com.uts.task_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uts.task_service.model.Task;
import com.uts.task_service.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    // CREATE
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        try {
            Task savedTask = taskService.createTask(task);
            logger.info("[CONTROLLER][CREATE] Task created | id={}", savedTask.getId());
            return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("[CONTROLLER][CREATE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(){
        try {
            List<Task> tasks = taskService.getAllTask();
            logger.info("[CONTROLLER][READ_ALL] Task count={}", tasks.size());
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[CONTROLLER][READ_ALL] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        try {
            Optional<Task> task = taskService.getTaskById(id);
            if (task.isPresent()) {
                logger.info("[CONTROLLER][READ_BY_ID] Task found | id={}", id);
                return new ResponseEntity<>(task.get(), HttpStatus.OK);
            } else {
                logger.warn("[CONTROLLER][READ_BY_ID] Task not found | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][READ_BY_ID] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        try {
            Optional<Task> updatedTask = taskService.updateTask(id, taskDetails);
            if(updatedTask.isPresent()){
                logger.info("[CONTROLLER][UPDATE] Task updated | id={}", id);
                return new ResponseEntity<>(updatedTask.get(), HttpStatus.OK);
            } else {
                logger.warn("[CONTROLLER][UPDATE] Task not found for update | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][UPDATE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        try {
            boolean deleted = taskService.deleteTask(id);
            if(deleted){
                logger.info("[CONTROLLER][DELETE] Task deleted | id={}", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.warn("[CONTROLLER][DELETE] Task not found for delete | id={}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("[CONTROLLER][DELETE] Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
