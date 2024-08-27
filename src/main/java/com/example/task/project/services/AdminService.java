package com.example.task.project.services;

import com.example.task.project.customexceptions.InvalidDataException;
import com.example.task.project.customexceptions.WorkExists;
import com.example.task.project.entity.Client;
import com.example.task.project.entity.Task;
import com.example.task.project.repository.ClientRepository;
import com.example.task.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmailService emailService;

    public void assignTask(String userName, String taskId) throws InvalidDataException, WorkExists {
        Client userNameExist = clientRepository.findByUserName(userName);
        Task taskIdExist = taskRepository.findByTaskId(taskId);
        if (userNameExist == null){
            throw new InvalidDataException("User does not exists.");
        }
        if (taskIdExist == null){
            throw new InvalidDataException("Task does not exists.");
        }

        if (!taskIdExist.isTaskAvailable()){
            throw new WorkExists("The task is already assigned.");
        }

        if (userNameExist.getTasks().contains(taskIdExist)){
            throw new WorkExists("The task is already assigned to this user.");
        }

        if (!userNameExist.isUserAvailable()){
            throw new WorkExists("User is not available for more work.");
        }

        List<Task> allTasks = userNameExist.getTasks();
        allTasks.add(taskIdExist);
        userNameExist.setTasks(allTasks);
        userNameExist.setUserAvailable(false);
        clientRepository.save(userNameExist);
        taskIdExist.setTaskAvailable(false);
        taskRepository.save(taskIdExist);
        emailService.sendEmail(userNameExist.getEmail(), "New Task Assigned.", "Hi " + userName + ". Congratulations! You have a new task, " + taskIdExist.getTaskName() + ". Please complete it before " + taskIdExist.getDueDate() + ".");
    }


}
