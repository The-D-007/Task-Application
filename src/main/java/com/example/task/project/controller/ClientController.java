package com.example.task.project.controller;

import com.example.task.project.customexceptions.InvalidDataException;
import com.example.task.project.customexceptions.WorkExists;
import com.example.task.project.pojo.TaskDetails;
import com.example.task.project.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/task-completed")
    public ResponseEntity<String> completeTask(@RequestBody TaskDetails taskDetails){
        try {
            clientService.completeTask(taskDetails.getUserName(), taskDetails.getTaskId());
        }catch (InvalidDataException e){
            log.error("Invalid Data", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Task completed successfully", HttpStatus.OK);
    }
}
