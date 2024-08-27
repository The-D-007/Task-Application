package com.example.task.project.services;

import com.example.task.project.customexceptions.WorkExists;
import com.example.task.project.entity.Project;
import com.example.task.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void saveNewProject(Project project) throws DataAccessException {
        projectRepository.save(project);
    }
}