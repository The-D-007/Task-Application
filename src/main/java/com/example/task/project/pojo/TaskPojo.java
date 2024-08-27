package com.example.task.project.pojo;

import com.example.task.project.entity.Project;
import com.example.task.project.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPojo {
    private String taskName;
    private String dueDate;
    private String projectName;

}
