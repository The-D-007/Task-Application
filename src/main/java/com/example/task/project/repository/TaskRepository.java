package com.example.task.project.repository;

import com.example.task.project.entity.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, ObjectId> {
    Task findByTaskName(String taskName);
    Task findByTaskId(String taskId);
}
