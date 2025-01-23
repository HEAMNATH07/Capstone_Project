package com.example.Task.Service;

import com.example.Task.Enum.TaskStatus;
import com.example.Task.Model.Task;
import com.example.Task.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    } 
    
    public List<Task> getTasksByEventId(String eventId) {
        return taskRepository.findByEventId(eventId);
    } 

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(String id, Task task) {
        if (taskRepository.existsById(id)) {
            task.setId(id); 
            return taskRepository.save(task);
        }
        return null; 
    }

    public boolean deleteTask(String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false; 
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public Task updateTaskStatus(String id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id); 
        TaskStatus taskStatus = TaskStatus.valueOf(status);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(taskStatus); 
            return taskRepository.save(task);
        }
        return null; 
    }
}