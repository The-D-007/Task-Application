package com.example.task.project.datacheck;

import com.example.task.project.customexceptions.DateFormatException;
import com.example.task.project.customexceptions.WorkDataException;
import com.example.task.project.entity.Project;
import com.example.task.project.entity.Task;
import com.example.task.project.pojo.TaskPojo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class TaskDataCheck {


    private LocalDate date = LocalDate.now();

    public Task checkTaskDetails(TaskPojo taskPojo) throws WorkDataException, DateFormatException{
        LocalDate taskDate;
        try{
            taskDate = LocalDate.parse(taskPojo.getDueDate());
        }catch  (DateTimeParseException e) {
            throw new DateFormatException("Invalid date format. Please use yyyy-MM-dd.");
        }
        if (!StringUtils.hasText(taskPojo.getTaskName()) || !StringUtils.hasText(taskPojo.getProjectName())){
            throw new WorkDataException("The task and project name must not be empty or null.");
        }
        if (taskDate.isBefore(date)){
            throw new WorkDataException("The due date must be after the current date.");
        }
        Task task = new Task();
        task.setTaskName(taskPojo.getTaskName());
        task.setDueDate(taskDate);
        task.setProjectName(taskPojo.getProjectName());
        return task;
    }

}
