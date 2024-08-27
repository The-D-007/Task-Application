package com.example.task.project.controller;

import com.example.task.project.customexceptions.DateFormatException;
import com.example.task.project.customexceptions.InvalidDataException;
import com.example.task.project.customexceptions.WorkDataException;
import com.example.task.project.customexceptions.WorkExists;
import com.example.task.project.datacheck.ProjectDataCheck;
import com.example.task.project.datacheck.TaskDataCheck;
import com.example.task.project.entity.Client;
import com.example.task.project.entity.Project;
import com.example.task.project.entity.Task;
import com.example.task.project.pojo.ClientPojo;
import com.example.task.project.pojo.TaskDetails;
import com.example.task.project.pojo.ProjectPojo;
import com.example.task.project.pojo.TaskPojo;
import com.example.task.project.services.AdminService;
import com.example.task.project.services.ClientService;
import com.example.task.project.services.ProjectService;
import com.example.task.project.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProjectDataCheck projectDataCheck;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskDataCheck taskDataCheck;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/create-project")
    public ResponseEntity<String> createNewProject(@RequestBody ProjectPojo projectPojo){
        try {
            Project project = projectDataCheck.checkProjectDetails(projectPojo);
            projectService.saveNewProject(project);
            return new ResponseEntity<>("Project created successfully", HttpStatus.OK);
        }catch (WorkDataException e){
            log.error("Invalid project data", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (DateFormatException e){
            log.error("Invalid date Format", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            log.error("Server is not responding", e);
        }
        return new ResponseEntity<>("Project Already exists", HttpStatus.CONFLICT);
    }

    @PostMapping("/create-task")
    public ResponseEntity<String> createNewTask(@RequestBody TaskPojo taskPojo){
        try {
            Task task = taskDataCheck.checkTaskDetails(taskPojo);
            taskService.saveNewTask(task);
            return new ResponseEntity<>("Task created successfully", HttpStatus.OK);
        }catch (WorkDataException e){
            log.error("Invalid project data", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (WorkExists e){
            log.error("Already exists", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.CONFLICT);
        }catch (DateFormatException e){

            log.error("Invalid date Format", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            log.error("Server is not responding", e);
        }
        return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/assign-task")
    public ResponseEntity<String> assignTask(@RequestBody TaskDetails assignTask){
        try {
            adminService.assignTask(assignTask.getUserName(), assignTask.getTaskId());
        }catch (InvalidDataException e){
            log.error("Invalid Data", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (WorkExists e){
            log.error("Already exists" , e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Task assigned successfully", HttpStatus.OK);
    }

    @GetMapping("/get-all-clients")
    public ResponseEntity<List<ClientPojo>> getAllClients(){
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

     @GetMapping("/get-all-available-clients")
    public ResponseEntity<List<ClientPojo>> getAllAvailableClients(){
        return new ResponseEntity<>(clientService.getAvailableClients(), HttpStatus.OK);
    }


    @GetMapping("/get-all-tasks")
    public ResponseEntity<List<Task>> getAllTasks(){
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }


    @GetMapping("/get-all-available-tasks")
    public ResponseEntity<List<Task>> getAllAvailableTasks(){
        return new ResponseEntity<>(taskService.getAvailableTasks(), HttpStatus.OK);
    }


    @GetMapping("/get-all-completed-tasks")
    public ResponseEntity<List<Task>> getAllCompletedTasks(){
        return new ResponseEntity<>(taskService.getCompletedTasks(), HttpStatus.OK);
    }


}
