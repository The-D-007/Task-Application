package com.example.task.project.repository;

import com.example.task.project.entity.Client;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, ObjectId> {
    Client findByUserName(String name);
}
