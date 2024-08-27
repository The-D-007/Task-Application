package com.example.task.project.repository;

import com.example.task.project.entity.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
    Project findByProjectName(String projectName);
}
