package com.example.Task.Repository;

import com.example.Task.Enum.TaskStatus;
import com.example.Task.Model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByEventId(String eventId);
}