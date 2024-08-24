package com.example.task.project.controller;

import com.example.task.project.customexceptions.InvalidClientDataException;
import com.example.task.project.entity.Client;
import com.example.task.project.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class PublicController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Client client){
        try {
            boolean userSaved = clientService.saveNewUser(client);
            if (userSaved) {
                return new ResponseEntity<>("User is Created ", HttpStatus.OK);
            }
        }catch (InvalidClientDataException e) {
            log.error("Invalid client data: ", e);
            return new ResponseEntity<>("Invalid client data", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            log.error("Error in creating user: ", e);
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }


}
