package com.example.task.project.services;

import com.example.task.project.customexceptions.WorkExists;
import com.example.task.project.entity.Project;
import com.example.task.project.entity.Task;
import com.example.task.project.repository.ProjectRepository;
import com.example.task.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public void saveNewTask(Task task) throws WorkExists, DataAccessException {
        Project projectExist = projectRepository.findByProjectName(task.getProjectName());
        Task taskExist = taskRepository.findByTaskName(task.getTaskName());
        if (projectExist == null){
            throw new WorkExists("The project does not exists.");
        }
        if (taskExist != null && taskExist.getProjectName().equals(projectExist.getProjectName())){
                throw new WorkExists("The task already exists in this project.");
            }
        if (task.getDueDate().isAfter(projectExist.getDueDate())){
            throw new WorkExists("The task's due date must be after the project's due date.");
        }

        List<Task> allTasks = projectExist.getTasks();
        taskRepository.save(task);
        allTasks.add(task);
        projectExist.setTasks(allTasks);
        projectRepository.save(projectExist);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getAvailableTasks(){
        List<Task> tasks = getAllTasks();
        List<Task> taskAvailable = new ArrayList<>();
        for(Task client: tasks){
            if (client.isTaskAvailable()){
                taskAvailable.add(client);
            }
        }
        return taskAvailable;
    }

    public List<Task> getCompletedTasks(){
        List<Task> tasks = getAllTasks();
        List<Task> taskCompleted = new ArrayList<>();
        for(Task client: tasks){
            if (client.isTaskCompleted()){
                taskCompleted.add(client);
            }
        }
        return taskCompleted;
    }

}
