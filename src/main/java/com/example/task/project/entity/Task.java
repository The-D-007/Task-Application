package com.example.task.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    private ObjectId taskId;

    @NonNull
    @Indexed(unique = true)
    private String taskName;
    @NonNull
    private LocalDate dueDate;

    private boolean isTaskAvailable = true;
    private boolean isTaskCompleted = false;

    @NonNull
    private String projectName;

}
