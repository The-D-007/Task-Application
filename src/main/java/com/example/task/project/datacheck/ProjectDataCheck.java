package com.example.task.project.datacheck;

import com.example.task.project.customexceptions.DateFormatException;
import com.example.task.project.customexceptions.WorkDataException;
import com.example.task.project.entity.Project;
import com.example.task.project.pojo.ProjectPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class ProjectDataCheck {


    private LocalDate date = LocalDate.now();

    public Project checkProjectDetails(ProjectPojo projectPojo) throws WorkDataException, DateFormatException{
        LocalDate projectDate;
        try{
            projectDate = LocalDate.parse(projectPojo.getDueDate());
        }catch  (DateTimeParseException e) {
            throw new DateFormatException("Invalid date format. Please use yyyy-MM-dd.");
        }
        if (!StringUtils.hasText(projectPojo.getProjectName())){
            throw new WorkDataException("The project name must not be empty or null.");
        }
        if (projectDate.isBefore(date)){
            throw new WorkDataException("The due date must be after the current date.");
        }
        Project project = new Project();
        project.setProjectName(projectPojo.getProjectName());
        project.setDueDate(projectDate);
        return project;
    }

}
