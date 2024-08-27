package com.example.task.project.pojo;

import com.example.task.project.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientPojo {
    private String userName;
    private String password;
    private String email;
    private List<String> roles;
    private List<Task> tasks;
    private List<Task> completedTasks;
    private boolean isUserAvailable;

    public ClientPojo( String userName, String email, List<String> roles, List<Task> tasks, List<Task> completedTasks, boolean isUserAvailable) {
        this.userName = userName;
        this.email = email;
        this.roles = roles;
        this.tasks = tasks;
        this.completedTasks = completedTasks;
        this.isUserAvailable = isUserAvailable;
    }
}
