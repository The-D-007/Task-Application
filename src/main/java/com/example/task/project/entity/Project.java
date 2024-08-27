package com.example.task.project.entity;

import com.example.task.project.pojo.ProjectPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String projectName;
    @NonNull
    private LocalDate dueDate;

    @DBRef
    private List<Task> tasks = new ArrayList<>();


}
